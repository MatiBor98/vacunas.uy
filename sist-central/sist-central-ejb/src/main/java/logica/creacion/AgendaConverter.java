package logica.creacion;

import datos.dtos.AgendaDTO;
import datos.dtos.HoraInicioFinDTO;
import datos.entidades.Agenda;
import datos.entidades.InformacionPosiblesIntervalos;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class AgendaConverter implements Converter<Agenda, AgendaDTO> {
    @Inject
    private Converter<InformacionPosiblesIntervalos, HoraInicioFinDTO> informacionPosiblesIntervalosConverter;

    @Override
    public AgendaDTO convert(Agenda agenda) {
        AgendaDTOBuilder builder = new AgendaDTOBuilder();

        Map<DayOfWeek, HoraInicioFinDTO> horarioPorDia = agenda.getHorarioPorDia()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> informacionPosiblesIntervalosConverter.convert(entry.getValue())));

        return builder.setInicio(agenda.getInicio())
                .setFin(agenda.getFin())
                .setHorarioPorDia(horarioPorDia)
                .setEtapaId(agenda.getEtapa().getId())
                .createAgendaDTO();
    }
}
