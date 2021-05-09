package logica.creacion;

import datos.dtos.HoraInicioFinDTO;

import java.time.LocalTime;

public class HoraInicioFinDTOBuilder {
    private LocalTime inicio;
    private LocalTime fin;
    private int capasidadPorTurno;
    private int minutosTurno;

    public HoraInicioFinDTOBuilder setInicio(LocalTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public HoraInicioFinDTOBuilder setFin(LocalTime fin) {
        this.fin = fin;
        return this;
    }

    public HoraInicioFinDTOBuilder setCapasidadPorTurno(int capasidadPorTurno) {
        this.capasidadPorTurno = capasidadPorTurno;
        return this;
    }

    public HoraInicioFinDTOBuilder setMinutosTurno(int minutosTurno) {
        this.minutosTurno = minutosTurno;
        return this;
    }

    public HoraInicioFinDTO createHoraInicioFinDTO() {
        return new HoraInicioFinDTO(inicio, fin, capasidadPorTurno, minutosTurno);
    }
}