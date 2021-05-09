package logica.creacion;

import datos.dtos.HoraInicioFinDTO;

import java.time.LocalTime;

public class InformacionPosiblesIntervalosDTOBuilder {
    private LocalTime inicio;
    private LocalTime fin;
    private int capasidadPorTurno;
    private int minutosTurno;

    public InformacionPosiblesIntervalosDTOBuilder setInicio(LocalTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public InformacionPosiblesIntervalosDTOBuilder setFin(LocalTime fin) {
        this.fin = fin;
        return this;
    }

    public InformacionPosiblesIntervalosDTOBuilder setCapasidadPorTurno(int capasidadPorTurno) {
        this.capasidadPorTurno = capasidadPorTurno;
        return this;
    }

    public InformacionPosiblesIntervalosDTOBuilder setMinutosTurno(int minutosTurno) {
        this.minutosTurno = minutosTurno;
        return this;
    }

    public HoraInicioFinDTO createHoraInicioFinDTO() {
        return new HoraInicioFinDTO(inicio, fin, capasidadPorTurno, minutosTurno);
    }
}