package datos.entidades;

import java.time.LocalTime;

import javax.persistence.Embeddable;

@Embeddable
public class HoraInicioFin {
    private LocalTime inicio;
    private LocalTime fin;
    private int capasidadPorTurno;
    private int minutosTurno;

    public HoraInicioFin(LocalTime inicio, LocalTime fin, int capasidadPorTurno, int minutosTurno) {
        this.inicio = inicio;
        this.fin = fin;
        this.capasidadPorTurno = capasidadPorTurno;
        this.minutosTurno = minutosTurno;
    }

    public HoraInicioFin() {

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

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    public void setCapasidadPorTurno(int capasidadPorTurno) {
        this.capasidadPorTurno = capasidadPorTurno;
    }

    public void setMinutosTurno(int minutosTurno) {
        this.minutosTurno = minutosTurno;
    }
}