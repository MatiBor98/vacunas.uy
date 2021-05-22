package datos.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

@Entity
public class Agenda implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaAgendaId")
    @SequenceGenerator(name="sequenciaAgendaId",sequenceName="sequenciaAgendaId", allocationSize=1)
    private int id;

    private String nombre;

    private LocalDate inicio;

    private LocalDate fin;

    @ManyToOne
    @JoinColumn(name="etapaId", nullable=false)
    private Etapa etapa;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "horarioPorDia")
    @MapKeyColumn(name = "dia")
    @MapKeyEnumerated(EnumType.ORDINAL)
    private Map<DayOfWeek, InformacionPosiblesIntervalos> horarioPorDia;

    @ManyToOne
    private Turno turno;

    public Agenda() {}

    public Agenda(int id, String nombre, LocalDate inicio, LocalDate fin, Etapa etapa,
                  Map<DayOfWeek, InformacionPosiblesIntervalos> horarioPorDia, Turno turno) {
        this.id = id;
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
        this.etapa = etapa;
        this.horarioPorDia = horarioPorDia;
        this.turno = turno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Map<DayOfWeek, InformacionPosiblesIntervalos> getHorarioPorDia() {
        return horarioPorDia;
    }

    public void setHorarioPorDia(Map<DayOfWeek, InformacionPosiblesIntervalos> horarioPorDia) {
        this.horarioPorDia = horarioPorDia;
    }

    public InformacionPosiblesIntervalos addHorarioPorDia(DayOfWeek dia, InformacionPosiblesIntervalos horario) {
        return horarioPorDia.put(dia, horario);
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    private long calcularIntervalosDisponbilesPorSemana(Map<DayOfWeek, InformacionPosiblesIntervalos> horarioPorDia) {
        return horarioPorDia.values()
                .stream()
                .map(InformacionPosiblesIntervalos::calcularCantidadIntervalos)
                .reduce(0L, Long::sum);
    }
}
