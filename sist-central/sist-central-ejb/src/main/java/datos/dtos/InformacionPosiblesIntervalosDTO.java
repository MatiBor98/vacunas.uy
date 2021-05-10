package datos.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalTime;

public class InformacionPosiblesIntervalosDTO implements Serializable {
    private final LocalTime inicio;
    private final LocalTime fin;
    private final int capasidadPorTurno;
    private final int minutosTurno;

    @JsonCreator
    public InformacionPosiblesIntervalosDTO(
            @JsonProperty("inicio")LocalTime inicio,
            @JsonProperty("fin")LocalTime fin,
            @JsonProperty("capasidadPorTurno")int capasidadPorTurno,
            @JsonProperty("minutosTurno")int minutosTurno) {
        this.inicio = inicio;
        this.fin = fin;
        this.capasidadPorTurno = capasidadPorTurno;
        this.minutosTurno = minutosTurno;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public int getCapasidadPorTurno() {
        return capasidadPorTurno;
    }

    public int getMinutosTurno() {
        return minutosTurno;
    }

    @Override
    public String toString() {
        return "HoraInicioFinDTO{" +
                "inicio=" + inicio +
                ", fin=" + fin +
                ", capasidadPorTurno=" + capasidadPorTurno +
                ", minutosTurno=" + minutosTurno +
                '}';
    }
}
