package logica.creacion;

import datos.entidades.HoraInicioFin;

import java.time.LocalTime;

public class HoraInicioFinBuilder {
    private LocalTime inicio;
    private LocalTime fin;
    private int capasidadPorTurno;
    private int minutosTurno;

    public HoraInicioFinBuilder setInicio(LocalTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public HoraInicioFinBuilder setFin(LocalTime fin) {
        this.fin = fin;
        return this;
    }

    public HoraInicioFinBuilder setCapasidadPorTurno(int capasidadPorTurno) {
        this.capasidadPorTurno = capasidadPorTurno;
        return this;
    }

    public HoraInicioFinBuilder setMinutosTurno(int minutosTurno) {
        this.minutosTurno = minutosTurno;
        return this;
    }

    public HoraInicioFin createHoraInicioFin() {
        return new HoraInicioFin(inicio, fin, capasidadPorTurno, minutosTurno);
    }
}