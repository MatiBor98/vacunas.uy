package logica.negocios;

import datos.dtos.*;
import datos.entidades.*;
import datos.repositorios.AgendaRepositoryLocal;
import datos.repositorios.CiudadanoRepositoryLocal;
import datos.repositorios.IntervaloRepository;
import datos.repositorios.ReservaRepository;
import logica.creacion.Converter;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.EtapaController;
import logica.servicios.local.TurnoServiceLocal;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Stateless
public class AgendaBean implements AgendaServiceLocal {
    @EJB
    private AgendaRepositoryLocal agendaRepository;

    @EJB
    private IntervaloRepository intervaloRepository;

    @EJB
    private CiudadanoRepositoryLocal ciudadanoRepository;

    @EJB
    private ReservaRepository reservaRepository;
    
    @EJB
    private EtapaController etapaController;
    
    @EJB
    private TurnoServiceLocal turnoService;

    @Inject
    private Converter<AgendaDTO, Agenda> agendaConverter;

    @Inject
    private Converter<Agenda, AgendaDTO> agendaDTOConverter;

    @Inject
    private Converter<Vacunatorio, VacunatorioDTO> vacunatorioVacunatorioDTOConverter;

    public AgendaBean() {
    }

    @Override
    public List<AgendaDTO> find() {
        return agendaRepository.find().parallelStream().map(agendaDTOConverter::convert).collect(Collectors.toList());
    }

    @Override
    public Optional<AgendaDTO> find(int id) {
        return agendaRepository.find(id).map(agendaDTOConverter::convert);
    }

    @Override
    public List<AgendaDTO> findByNombrePlan(String criterio) {
        return agendaRepository.findByNombrePlan(criterio).parallelStream().map(agendaDTOConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public AgendaDTO save(AgendaDTO agendaDTO) {
        Agenda agendaCreada = agendaConverter.convert(agendaDTO);
        agendaRepository.save(agendaCreada);
        return agendaDTOConverter.convert(agendaCreada);
    }

    @Override
    public List<VacunatorioTieneAgendaDTO> findAgendasParaCiudadanoPorDepartamento(String nombreEnfermedad, int edadCiudadano,
                                                                                   Trabajo trabajos, Departamento departamento) {
         return agendaRepository.findAgendasParaCiudadanoPorDepartamento(nombreEnfermedad, edadCiudadano,
                trabajos, departamento).stream()
                .map(agenda -> new VacunatorioTieneAgendaDTO(
                        vacunatorioVacunatorioDTOConverter.convert(agenda.getTurno().getVacunatorio()),
                        agendaDTOConverter.convert(agenda)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserva> efectuarReserva(Intervalo intervalo, int ciudadanoCi) {
        Ciudadano ciudadano = ciudadanoRepository.find(ciudadanoCi).orElseThrow(RuntimeException::new);
        Vacuna vacuna = intervalo.getAgenda().getEtapa().getVacuna();
        List<Reserva> reservasHechas = new ArrayList<>(vacuna.getCantDosis());

        Intervalo intervaloCreado = intervaloRepository.findOrCreate(intervalo);
        Reserva reserva = new Reserva(Estado.PENDIENTE, ciudadano, intervaloCreado, 1);
        intervaloCreado.addReserva(reserva);
        reservaRepository.save(reserva);
        reservasHechas.add(reserva);

        for(int i = 1; i < vacuna.getCantDosis(); i++) {
            LocalDateTime fechaSiguienteDosis = intervalo.getFechayHora()
                    .plusDays((long) vacuna.getDosisSeparacionDiasMultiploSemana() * i);
            Intervalo intervaloCreadoSiguienteDosis = intervaloRepository.findOrCreate(
                    new Intervalo(fechaSiguienteDosis, intervalo.getAgenda()));
            Reserva reservaSigueinteDosis = new Reserva(Estado.PENDIENTE, ciudadano, intervaloCreadoSiguienteDosis,
                    i + 1);
            intervaloCreadoSiguienteDosis.addReserva(reservaSigueinteDosis);
            reservaRepository.save(reservaSigueinteDosis);
            reservasHechas.add(reservaSigueinteDosis);
        }

        return reservasHechas;
    }

    @Override
    public List<Intervalo> getIntervalos(int agendaId, LocalDate fechaInicioSemana) {
        if(!fechaInicioSemana.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            throw new RuntimeException("La fecha de la semana debe comenzar en Lunes.");
        }

        LocalDate fechaInicio = fechaInicioSemana.isBefore(LocalDate.now()) ?
                LocalDate.now().plusDays(1) :
                fechaInicioSemana;
        Agenda agenda = agendaRepository.find(agendaId).orElseThrow(RuntimeException::new);
        Vacuna vacuna = agenda.getEtapa().getVacuna();
        LocalDate fechaLimite = fechaInicioSemana.plusWeeks(1);

        Map<LocalDateTime, Intervalo> intervalosRegistrados = intervaloRepository
                .getIntervalos(fechaInicio.atStartOfDay(), fechaLimite.atStartOfDay(), agendaId)
                .stream().collect(Collectors.toMap(Intervalo::getFechayHora, Function.identity()));

        crearAgregarIntervalosNuevos(fechaInicio, fechaLimite, agenda, intervalosRegistrados);

        Predicate<Intervalo> isIntervaloDisponible = getIsIntervaloDisponible(fechaInicio, agendaId,
                vacuna.getCantDosis(), vacuna.getDosisSeparacionDiasMultiploSemana());

        return intervalosRegistrados.values()
                .parallelStream()
                .filter(isIntervaloDisponible)
                .sorted(Comparator.comparing(Intervalo::getFechayHora))
                .collect(Collectors.toList());
    }

    private void crearAgregarIntervalosNuevos(LocalDate fechaInicio, LocalDate fechaLimite, Agenda agenda,
                                         Map<LocalDateTime, Intervalo> intervalosRegistrados) {
        for(AtomicReference<LocalDate> l = new AtomicReference<>(fechaInicio);
            l.get().isBefore(fechaLimite);
            l.getAndSet(l.get().plusDays(1))) {

            Optional.ofNullable(agenda.getHorarioPorDia().get(l.get().getDayOfWeek()))
                    .ifPresent(posiblesIntervalos -> {
                        for(LocalTime t = posiblesIntervalos.getInicio(); t.isBefore(posiblesIntervalos.getFin());
                            t = t.plusMinutes(posiblesIntervalos.getMinutosTurno())) {
                            intervalosRegistrados.putIfAbsent(l.get().atTime(t),
                                    new Intervalo(l.get().atTime(t), agenda));
                        }
                    });
        }
    }

    /**
     * Retorna un predicado que dado un intervalo chequea si este esta disponible y ademas que tenga intervalos disponibles
     * para las siguientes dosis.
     * @param fechaInicio  fecha inicio de la ventana de tipo que se va a mostrar los intervalos (Un Lunes )
     * @param agendaId Id de la agenda de la que se estan listando los intervalos
     * @param cantidadDosis Cantidad de dosis para la vacuna.
     * @param dosisSeparacionDias Cantidad de días para la siguiente dosis.
     * @return El predicado para poder filtrar los intervalos disponibles
     */
    private Predicate<Intervalo> getIsIntervaloDisponible(LocalDate fechaInicio, int agendaId, int cantidadDosis,
                                                              int dosisSeparacionDias) {
        Map<LocalDateTime, Intervalo> intervalosRegistradosDosisSiguientes = new HashMap<>();
        for(int i = 1; i < cantidadDosis; i++) {
            LocalDate fechaInicioDosis = fechaInicio.plusDays((long) dosisSeparacionDias * i);
            LocalDate fechaLimiteDosis = fechaInicioDosis.plusWeeks(1);
            intervaloRepository
                    .getIntervalos(fechaInicioDosis.atStartOfDay(), fechaLimiteDosis.atStartOfDay(), agendaId)
                    .forEach(
                            intervalo -> intervalosRegistradosDosisSiguientes.put(intervalo.getFechayHora(), intervalo)
                    );
        }

        return (intervalo) -> {
            Agenda agenda = intervalo.getAgenda();
            DayOfWeek dayOfWeek = intervalo.getFechayHora().getDayOfWeek();

            if (intervalo.getCantidadReservasPendientes() >= agenda.getHorarioPorDia().get(dayOfWeek).getCapacidadPorTurno()) {
                return false;
            }

            for(int i = 1; i < cantidadDosis; i++) {
                Intervalo intervaloDosis = intervalosRegistradosDosisSiguientes.get(
                        intervalo.getFechayHora().plusDays((long) dosisSeparacionDias * i)
                );

                int cantidaReservas = Optional.ofNullable(intervaloDosis).map(Intervalo::getCantidadReservasPendientes)
                        .orElse(0);

                if (cantidaReservas >= agenda.getHorarioPorDia().get(dayOfWeek).getCapacidadPorTurno()) {
                    return false;
                }
            }

            return true;
        };
    }
    
    public AgendaDTO2 getAgendaDTO2(Agenda agenda) {
    	EtapaDTO2 etapaDTO = etapaController.getEtapaDTO2(agenda.getEtapa());
    	TurnoDTO turnoDTO = turnoService.getTurnoDTO(agenda.getTurno());
    	String fin;
    	if(agenda.getFin() == null) {
    		fin = "";
    	} else {
    		fin = agenda.getFin().toString();
    	}
    	AgendaDTO2 res = new AgendaDTO2(agenda.getNombre(), agenda.getInicio().toString(), fin, etapaDTO, turnoDTO);
    	return res;
    }

	@Override
	public IntervaloDTO2 getIntervaloDTO(Intervalo intervalo) {
		AgendaDTO2 agendaDTO = getAgendaDTO2(intervalo.getAgenda());
		IntervaloDTO2 intDTO = new IntervaloDTO2(intervalo.getFechayHora().toString(), agendaDTO);
		return intDTO;
	}
}