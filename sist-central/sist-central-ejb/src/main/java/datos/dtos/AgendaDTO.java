package datos.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class AgendaDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Map<DayOfWeek, InformacionPosiblesIntervalosDTO> horarioPorDia;
    private final int etapaId;
    private final int turnoId;
    private final LocalDate inicio;
    private final LocalDate fin;
    private final String nombre;
    private final int id;

    @JsonCreator
    public AgendaDTO(
            int id,
            Map<DayOfWeek, InformacionPosiblesIntervalosDTO> horarioPorDia,
            int turnoId,
            int etapaId,
            LocalDate inicio,
            LocalDate fin,
            String nombre) {
        this.horarioPorDia = horarioPorDia;
        this.turnoId = turnoId;
        this.etapaId = etapaId;
        this.inicio = inicio;
        this.fin = fin;
        this.id = id;
        this.nombre = nombre;
    }

    public Map<DayOfWeek, InformacionPosiblesIntervalosDTO> getHorarioPorDia() {
        return horarioPorDia;
    }

    public int getEtapaId() {
        return etapaId;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public int getTurnoId() {
        return turnoId;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgendaDTO agendaDTO = (AgendaDTO) o;
        return getEtapaId() == agendaDTO.getEtapaId() && getTurnoId() == agendaDTO.getTurnoId() &&
                getId() == agendaDTO.getId() && getHorarioPorDia().equals(agendaDTO.getHorarioPorDia()) &&
                getInicio().equals(agendaDTO.getInicio()) && Objects.equals(getFin(), agendaDTO.getFin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHorarioPorDia(), getEtapaId(), getTurnoId(), getInicio(), getFin(), getId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AgendaDTO{");
        sb.append("horarioPorDia=").append(horarioPorDia);
        sb.append(", etapaId=").append(etapaId);
        sb.append(", turnoId=").append(turnoId);
        sb.append(", inicio=").append(inicio);
        sb.append(", fin=").append(fin);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    public String getHorariosString() {
        final StringBuilder sb = new StringBuilder("");
        horarioPorDia.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> sb
            .append("\n")
            .append(esUy(entry.getKey(), TextStyle.SHORT))
            .append(" - ")
            .append(entry.getValue().getInicio())
            .append(" a ")
            .append(entry.getValue().getFin()));
        return sb.toString();
    }

    public List<String> getHorariosAsString() {
        return horarioPorDia.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry ->
                        esUy(entry.getKey(), TextStyle.FULL) +
                        ": de " +
                        entry.getValue().getInicio() +
                        " a " +
                        entry.getValue().getFin())
                .collect(Collectors.toList());
    }

    private String esUy(DayOfWeek day, TextStyle style) {
        return day.getDisplayName(style, new Locale("es", "UY"));
    }
}
