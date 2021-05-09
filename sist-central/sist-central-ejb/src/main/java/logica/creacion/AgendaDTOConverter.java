package logica.creacion;

import datos.dtos.AgendaDTO;
import datos.entidades.Agenda;
import datos.entidades.Etapa;
import datos.entidades.HoraInicioFin;
import datos.repositorios.EtapaRepository;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class AgendaDTOConverter implements Converter<AgendaDTO, Agenda> {
    @EJB
    private EtapaRepository etapaRepository;

    @Inject
    private HorarioInicioFinDTOConverter horarioInicioFinDTOConverter;

    @Override
    public Agenda convert(AgendaDTO agendaDTO) {
        AgendaBuilder builder = new AgendaBuilder();
        Etapa etapa = etapaRepository.find(agendaDTO.getEtapaId()).orElseThrow(this::noSeEncontroEtapaRuntimeException);
        Map<DayOfWeek, HoraInicioFin> horariosPorDia = agendaDTO.getHorarioPorDia().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                horarioEnDia -> horarioInicioFinDTOConverter.convert(horarioEnDia.getValue())));
        return builder.setInicio(agendaDTO.getInicio())
                .setFin(agendaDTO.getFin())
                .setHorarioPorDia(horariosPorDia)
                .setEtapa(etapa)
                .createAgenda();
    }

    private RuntimeException noSeEncontroEtapaRuntimeException() {
        return new RuntimeException("No se encontro etapa.");
    }
}
