package datos.entidades;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.SequenceGenerator;

@Entity
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaAgendaId")
    @SequenceGenerator(name="sequenciaAgendaId",sequenceName="seq_agenda_id", allocationSize=1)
    private long id;

    private String nombre;

    private LocalDate inicio;

    private LocalDate fin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="etapa_id", nullable=false)
    private Etapa etapa;

    @ElementCollection
    @CollectionTable(name = "horario_por_dia")
    @MapKeyColumn(name = "dia")
    @MapKeyEnumerated(EnumType.ORDINAL)
    private Map<DayOfWeek, HoraInicioFin> horarioPorDia;

    public Agenda() {}

    public Agenda(long id, String nombre, LocalDate inicio, LocalDate fin, Etapa etapa, Map<DayOfWeek, HoraInicioFin> horarioPorDia) {
        this.id = id;
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
        this.etapa = etapa;
        this.horarioPorDia = horarioPorDia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public Map<DayOfWeek, HoraInicioFin> getHorarioPorDia() {
        return horarioPorDia;
    }

    public void setHorarioPorDia(Map<DayOfWeek, HoraInicioFin> horarioPorDia) {
        this.horarioPorDia = horarioPorDia;
    }

    public HoraInicioFin addHorarioPorDia(DayOfWeek dia, HoraInicioFin horario) {
        return horarioPorDia.put(dia, horario);
    }
}
