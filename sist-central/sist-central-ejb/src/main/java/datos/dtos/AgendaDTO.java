package datos.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class AgendaDTO implements Serializable {
    private final Map<DayOfWeek, InformacionPosiblesIntervalosDTO> horarioPorDia;
    private final long etapaId;
    private final long turnoId;
    private final LocalDate inicio;
    private final LocalDate fin;

    @JsonCreator
    public AgendaDTO(
            @JsonProperty("horarioPorDia")Map<DayOfWeek, InformacionPosiblesIntervalosDTO> horarioPorDia,
            @JsonProperty("vacunatorioId") long turnoId,
            @JsonProperty("etapaId") long etapaId,
            @JsonProperty("inicio") LocalDate inicio,
            @JsonProperty("fin") LocalDate fin) {
        this.horarioPorDia = horarioPorDia;
        this.turnoId = turnoId;
        this.etapaId = etapaId;
        this.inicio = inicio;
        this.fin = fin;
    }

    public Map<DayOfWeek, InformacionPosiblesIntervalosDTO> getHorarioPorDia() {
        return horarioPorDia;
    }

    public long getEtapaId() {
        return etapaId;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public long getTurnoId() {
        return turnoId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AgendaDTO{");
        sb.append("horarioPorDia=").append(horarioPorDia);
        sb.append(", etapaId=").append(etapaId);
        sb.append(", turnoId=").append(turnoId);
        sb.append(", inicio=").append(inicio);
        sb.append(", fin=").append(fin);
        sb.append('}');
        return sb.toString();
    }
}
