package logica.negocios;

import datos.dtos.*;
import datos.entidades.*;
import datos.repositorios.AgendaRepositoryLocal;
import logica.creacion.Converter;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.EtapaController;
import logica.servicios.local.TurnoServiceLocal;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class AgendaBean implements AgendaServiceLocal {
    @EJB
    private AgendaRepositoryLocal agendaRepository;

    @EJB
    private EtapaController etapaController;

    @EJB
    private TurnoServiceLocal turnoService;

    @Inject
    private Converter<AgendaDTO, Agenda> agendaConverter;

    @Inject
    private Converter<Agenda, AgendaDTO> agendaDTOConverter;

    @Inject
    private Converter<Etapa, EtapaDTO> etapaEtapaDTOConverter;

    @Inject
    private Converter<Vacunatorio, VacunatorioDTO> vacunatorioVacunatorioDTOConverter;

    public AgendaBean() {
    }

    @Override
    public List<AgendaDTO> find() {
        return agendaRepository.find().parallelStream().map(agendaDTOConverter::convert).collect(Collectors.toList());
    }

    @Override
    public void eliminar(int agendaId) {
        agendaRepository.find(agendaId).ifPresent(agenda -> agenda.setFin(LocalDate.now()));
    }

    @Override
    public List<VacunatorioTieneAgendaParaEtapaDTO> find(int offSet, int size) {
        return agendaRepository.find(offSet, size).parallelStream()
                .map(agenda -> new VacunatorioTieneAgendaParaEtapaDTO(
                        vacunatorioVacunatorioDTOConverter.convert(agenda.getTurno().getVacunatorio()),
                        agendaDTOConverter.convert(agenda),
                        etapaEtapaDTOConverter.convert(agenda.getEtapa())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public long findCount(String criterio) {
        return agendaRepository.findCount(criterio);
    }

    @Override
    public List<VacunatorioTieneAgendaParaEtapaDTO> findByNombrePlan(int offSet, int size, String criterio) {
        return agendaRepository.findByNombrePlan(offSet, size, criterio).parallelStream()
                .map(agenda -> new VacunatorioTieneAgendaParaEtapaDTO(
                        vacunatorioVacunatorioDTOConverter.convert(agenda.getTurno().getVacunatorio()),
                        agendaDTOConverter.convert(agenda),
                        etapaEtapaDTOConverter.convert(agenda.getEtapa())
                ))
                .collect(Collectors.toList());
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

    public AgendaDTO2 getAgendaDTO2(Agenda agenda) {
        EtapaDTO2 etapaDTO = etapaController.getEtapaDTO2(agenda.getEtapa());
        TurnoDTO turnoDTO = turnoService.getTurnoDTO(agenda.getTurno());
        String fin;
        if (agenda.getFin() == null) {
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