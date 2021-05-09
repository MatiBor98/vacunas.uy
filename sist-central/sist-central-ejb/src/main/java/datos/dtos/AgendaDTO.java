package datos.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class AgendaDTO implements Serializable {
    private final Map<DayOfWeek, HoraInicioFinDTO> horarioPorDia;
    private final long etapaId;
    private final LocalDate inicio;
    private final LocalDate fin;

    @JsonCreator
    public AgendaDTO(
            @JsonProperty("horarioPorDia")Map<DayOfWeek, HoraInicioFinDTO> horarioPorDia,
            @JsonProperty("etapaId") long etapaId,
            @JsonProperty("inicio") LocalDate inicio,
            @JsonProperty("fin") LocalDate fin) {
        this.horarioPorDia = horarioPorDia;
        this.etapaId = etapaId;
        this.inicio = inicio;
        this.fin = fin;
    }

    public Map<DayOfWeek, HoraInicioFinDTO> getHorarioPorDia() {
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

    @Override
    public String toString() {
        return "AgendaDTO{" +
                "horarioPorDia=" + horarioPorDia +
                ", etapaId=" + etapaId +
                ", inicio=" + inicio +
                ", fin=" + fin +
                '}';
    }
}
