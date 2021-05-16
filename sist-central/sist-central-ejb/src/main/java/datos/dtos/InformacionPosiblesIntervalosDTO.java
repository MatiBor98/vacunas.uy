package datos.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class InformacionPosiblesIntervalosDTO implements Serializable {
    private final LocalTime inicio;
    private final LocalTime fin;
    private final int capacidadPorTurno;
    private final int minutosTurno;

    public InformacionPosiblesIntervalosDTO(
            LocalTime inicio,
            LocalTime fin,
            int capacidadPorTurno,
            int minutosTurno) {
        this.inicio = inicio;
        this.fin = fin;
        this.capacidadPorTurno = capacidadPorTurno;
        this.minutosTurno = minutosTurno;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public int getCapacidadPorTurno() {
        return capacidadPorTurno;
    }

    public int getMinutosTurno() {
        return minutosTurno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformacionPosiblesIntervalosDTO that = (InformacionPosiblesIntervalosDTO) o;
        return getCapacidadPorTurno() == that.getCapacidadPorTurno() && getMinutosTurno() == that.getMinutosTurno() && getInicio().equals(that.getInicio()) && getFin().equals(that.getFin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInicio(), getFin(), getCapacidadPorTurno(), getMinutosTurno());
    }

    @Override
    public String toString() {
        return "HoraInicioFinDTO{" +
                "inicio=" + inicio +
                ", fin=" + fin +
                ", capacidadPorTurno=" + capacidadPorTurno +
                ", minutosTurno=" + minutosTurno +
                '}';
    }
}
