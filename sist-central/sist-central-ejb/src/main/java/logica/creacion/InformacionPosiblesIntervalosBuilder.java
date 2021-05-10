package logica.creacion;

import datos.entidades.InformacionPosiblesIntervalos;

import java.time.LocalTime;

public class InformacionPosiblesIntervalosBuilder {
    private LocalTime inicio;
    private LocalTime fin;
    private int capasidadPorTurno;
    private int minutosTurno;

    public InformacionPosiblesIntervalosBuilder setInicio(LocalTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public InformacionPosiblesIntervalosBuilder setFin(LocalTime fin) {
        this.fin = fin;
        return this;
    }

    public InformacionPosiblesIntervalosBuilder setCapasidadPorTurno(int capasidadPorTurno) {
        this.capasidadPorTurno = capasidadPorTurno;
        return this;
    }

    public InformacionPosiblesIntervalosBuilder setMinutosTurno(int minutosTurno) {
        this.minutosTurno = minutosTurno;
        return this;
    }

    public InformacionPosiblesIntervalos createHoraInicioFin() {
        return new InformacionPosiblesIntervalos(inicio, fin, capasidadPorTurno, minutosTurno);
    }
}