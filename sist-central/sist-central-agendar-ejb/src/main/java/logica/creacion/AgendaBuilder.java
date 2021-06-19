package logica.creacion;

import datos.entidades.Agenda;
import datos.entidades.Etapa;
import datos.entidades.InformacionPosiblesIntervalos;
import datos.entidades.Turno;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class AgendaBuilder {
    private int id;
    private String nombre;
    private LocalDate inicio;
    private LocalDate fin;
    private Etapa etapa;
    private Turno turno;
    private Map<DayOfWeek, InformacionPosiblesIntervalos> horarioPorDia;

    public AgendaBuilder setId(int id) {
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

    public AgendaBuilder setHorarioPorDia(Map<DayOfWeek, InformacionPosiblesIntervalos> horarioPorDia) {
        this.horarioPorDia = horarioPorDia;
        return this;
    }

    public AgendaBuilder setTurno(Turno turno) {
        this.turno = turno;
        return this;
    }

    public Agenda createAgenda() {
        validar();
        return new Agenda(id, nombre, inicio, fin, etapa, horarioPorDia, turno);
    }

    private void validar() {
        //TODO: Agregar Validaciones
    }
}