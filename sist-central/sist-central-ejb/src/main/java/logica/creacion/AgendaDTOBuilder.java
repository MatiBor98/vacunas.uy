package logica.creacion;

import datos.dtos.AgendaDTO;
import datos.dtos.HoraInicioFinDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class AgendaDTOBuilder {
    private Map<DayOfWeek, HoraInicioFinDTO> horarioPorDia;
    private long etapaId;
    private LocalDate inicio;
    private LocalDate fin;

    public AgendaDTOBuilder setHorarioPorDia(Map<DayOfWeek, HoraInicioFinDTO> horarioPorDia) {
        this.horarioPorDia = horarioPorDia;
        return this;
    }

    public AgendaDTOBuilder setEtapaId(long etapaId) {
        this.etapaId = etapaId;
        return this;
    }

    public AgendaDTOBuilder setInicio(LocalDate inicio) {
        this.inicio = inicio;
        return this;
    }

    public AgendaDTOBuilder setFin(LocalDate fin) {
        this.fin = fin;
        return this;
    }

    public AgendaDTO createAgendaDTO() {
        return new AgendaDTO(horarioPorDia, etapaId, inicio, fin);
    }
}