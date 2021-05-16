package logica.creacion;

import datos.dtos.InformacionPosiblesIntervalosDTO;

import java.time.LocalTime;

public class InformacionPosiblesIntervalosDTOBuilder {
    private LocalTime inicio;
    private LocalTime fin;
    private int capacidadPorTurno;
    private int minutosTurno;

    public InformacionPosiblesIntervalosDTOBuilder setInicio(LocalTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public InformacionPosiblesIntervalosDTOBuilder setFin(LocalTime fin) {
        this.fin = fin;
        return this;
    }

    public InformacionPosiblesIntervalosDTOBuilder setCapacidadPorTurno(int capacidadPorTurno) {
        this.capacidadPorTurno = capacidadPorTurno;
        return this;
    }

    public InformacionPosiblesIntervalosDTOBuilder setMinutosTurno(int minutosTurno) {
        this.minutosTurno = minutosTurno;
        return this;
    }

    public InformacionPosiblesIntervalosDTO createHoraInicioFinDTO() {
        return new InformacionPosiblesIntervalosDTO(inicio, fin, capacidadPorTurno, minutosTurno);
    }
}