package logica.creacion;

import datos.dtos.AgendaDTO;
import datos.dtos.InformacionPosiblesIntervalosDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class AgendaDTOBuilder {
    private int id;
    private Map<DayOfWeek, InformacionPosiblesIntervalosDTO> horarioPorDia;
    private int turnoId;
    private int etapaId;
    private LocalDate inicio;
    private LocalDate fin;

    public AgendaDTOBuilder setHorarioPorDia(Map<DayOfWeek, InformacionPosiblesIntervalosDTO> horarioPorDia) {
        this.horarioPorDia = horarioPorDia;
        return this;
    }

    public AgendaDTOBuilder setEtapaId(int etapaId) {
        this.etapaId = etapaId;
        return this;
    }

    public AgendaDTOBuilder setTurnoId(int turnoId) {
        this.turnoId = turnoId;
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

    public AgendaDTOBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public AgendaDTO createAgendaDTO() {
        return new AgendaDTO(id, horarioPorDia, turnoId, etapaId, inicio, fin);
    }
}