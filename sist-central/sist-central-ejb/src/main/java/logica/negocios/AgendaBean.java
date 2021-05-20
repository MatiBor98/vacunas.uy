package logica.negocios;

import datos.dtos.AgendaDTO;
import datos.entidades.*;
import datos.repositorios.AgendaRepositoryLocal;
import datos.repositorios.IntervaloRepository;
import logica.creacion.Converter;
import logica.servicios.local.AgendaServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class AgendaBean implements AgendaServiceLocal {
    @EJB
    private AgendaRepositoryLocal agendaRepository;

    @EJB
    private IntervaloRepository intervaloRepository;

    @Inject
    private Converter<AgendaDTO, Agenda> agendaConverter;

    @Inject
    private Converter<Agenda, AgendaDTO> agendaDTOConverter;

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
    public Map<Vacunatorio, List<AgendaDTO>> findAgendasParaCiudadanoPorDepartamento(String nombreEnfermedad, int edadCiudadano,
                                                                                     Trabajos trabajos, Departamento departamento) {
         return agendaRepository.findAgendasParaCiudadanoPorDepartamento(nombreEnfermedad, edadCiudadano,
                trabajos, departamento).stream()
                .map(agenda -> new Object[] {agenda.getTurno().getVacunatorio(), agendaDTOConverter.convert(agenda)})
                .collect(Collectors.toMap(
                        pair-> (Vacunatorio) pair[0],
                        pair -> getAgendaComoLinkedList((AgendaDTO) pair[1]),
                        (as1, ag2) -> {
                            as1.addAll(ag2);
                            return as1;
                        }));
    }

    private List<AgendaDTO> getAgendaComoLinkedList(AgendaDTO agendaDTO) {
        return new LinkedList<>(){{
            add(agendaDTO);
        }};
    }

    @Override
    public List<Intervalo> getIntervalos(int agendaId, LocalDate fechaInicio) {
        Agenda agenda = agendaRepository.find(agendaId).orElseThrow(RuntimeException::new);
        LocalDate fechaLimite = min(agenda.getFin().plusDays(1), fechaInicio.plusWeeks(1));

        if(fechaLimite.isBefore(fechaInicio)) {
            return Collections.emptyList();
        }

        Map<LocalDateTime, Intervalo> intervalosRegistrados = intervaloRepository
                .getIntervalos(fechaInicio.atStartOfDay(), fechaLimite.atStartOfDay(), agendaId)
                .stream().collect(Collectors.toMap(Intervalo::getFechayHora, Function.identity()));
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

        return intervalosRegistrados.values()
                .parallelStream()
                .filter(this::intervaloDisponible)
                .sorted(Comparator.comparing(Intervalo::getFechayHora))
                .collect(Collectors.toList());
    }

    private boolean intervaloDisponible(Intervalo intervalo) {
        return intervalo.getReservas().size() < intervalo.getAgenda().getHorarioPorDia()
                .get(intervalo.getFechayHora().getDayOfWeek()).getCapacidadPorTurno();
    }

    private LocalDate min(LocalDate a, LocalDate b) {
        return a.compareTo(b) < 0 ? a : b;
    }
}