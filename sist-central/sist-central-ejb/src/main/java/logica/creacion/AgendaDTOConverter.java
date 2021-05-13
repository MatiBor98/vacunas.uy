package logica.creacion;

import datos.dtos.AgendaDTO;
import datos.dtos.InformacionPosiblesIntervalosDTO;
import datos.entidades.Agenda;
import datos.entidades.Etapa;
import datos.entidades.InformacionPosiblesIntervalos;
import datos.entidades.Turno;
import datos.repositorios.EtapaRepository;
import datos.repositorios.TurnoRepositoryLocal;

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

    @EJB
    private TurnoRepositoryLocal turnoRepository;

    @Inject
    private Converter<InformacionPosiblesIntervalosDTO, InformacionPosiblesIntervalos> informacionPosiblesIntervalosDTOConverter;

    @Override
    public Agenda convert(AgendaDTO agendaDTO) {
        AgendaBuilder builder = new AgendaBuilder();
        Etapa etapa = etapaRepository.find(agendaDTO.getEtapaId()).orElseThrow(this::noSeEncontroEtapaRuntimeException);
        Turno turno = turnoRepository.findById(agendaDTO.getTurnoId()).orElseThrow(this::noSeEncontroTurnoRuntimeException);
        Map<DayOfWeek, InformacionPosiblesIntervalos> horariosPorDia = agendaDTO.getHorarioPorDia()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        horarioEnDia -> informacionPosiblesIntervalosDTOConverter.convert(horarioEnDia.getValue())));
        return builder.setInicio(agendaDTO.getInicio())
                .setFin(agendaDTO.getFin())
                .setHorarioPorDia(horariosPorDia)
                .setTurno(turno)
                .setEtapa(etapa)
                .createAgenda();
    }

    private RuntimeException noSeEncontroEtapaRuntimeException() {
        return new RuntimeException("No se encontro etapa.");
    }

    private RuntimeException noSeEncontroTurnoRuntimeException() {
        return new RuntimeException("No se encontro turno.");
    }
}
