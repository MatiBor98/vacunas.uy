package logica.creacion;

import datos.entidades.Agenda;
import datos.entidades.Etapa;
import datos.entidades.HoraInicioFin;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class AgendaBuilder {
    private long id;
    private String nombre;
    private LocalDate inicio;
    private LocalDate fin;
    private Etapa etapa;
    private Map<DayOfWeek, HoraInicioFin> horarioPorDia;

    public AgendaBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public AgendaBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public AgendaBuilder setInicio(LocalDate inicio) {
        this.inicio = inicio;
        return this;
    }

    public AgendaBuilder setFin(LocalDate fin) {
        this.fin = fin;
        return this;
    }

    public AgendaBuilder setEtapa(Etapa etapa) {
        this.etapa = etapa;
        return this;
    }

    public AgendaBuilder setHorarioPorDia(Map<DayOfWeek, HoraInicioFin> horarioPorDia) {
        this.horarioPorDia = horarioPorDia;
        return this;
    }

    public Agenda createAgenda() {
        return new Agenda(id, nombre, inicio, fin, etapa, horarioPorDia);
    }
}