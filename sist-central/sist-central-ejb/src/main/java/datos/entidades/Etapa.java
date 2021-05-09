package datos.entidades;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Etapa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaEtapaId")
    @SequenceGenerator(name="sequenciaEtapaId",sequenceName="sequenciaEtapaId", allocationSize=1)
    private long id;

    private String restricciones;

    private String descripcion;

    private LocalDate inicio;

    private LocalDate fin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="plan_id", nullable=false)
    private PlanVacunacion planVacunacion;

    @OneToMany(mappedBy = "etapa")
    private List<Agenda> agendas;

    public Etapa() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(String restricciones) {
        this.restricciones = restricciones;
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

    public PlanVacunacion getPlanVacunacion() {
        return planVacunacion;
    }

    public void setPlanVacunacion(PlanVacunacion planVacunacion) {
        this.planVacunacion = planVacunacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void addAgenda(Agenda agenda) {
        agendas.add(agenda);
    }
}
