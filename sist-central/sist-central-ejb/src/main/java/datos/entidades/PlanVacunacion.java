package datos.entidades;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;



@Entity
public class PlanVacunacion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaPlanId")
    @SequenceGenerator(name="sequenciaPlanId",sequenceName="sequenciaPlanId", allocationSize=1)
    private long id;

    private String nombre;

    private LocalDate inicio;

    private LocalDate fin;

    @OneToMany
    private List<Etapa> etapas;

    @ManyToOne
    @JoinColumn(name="enfermedadNombre", nullable=false)
    private Enfermedad enfermedad;

    public PlanVacunacion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Etapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<Etapa> etapas) {
        this.etapas = etapas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
	
	public Enfermedad getEnfermedad() {
        return this.enfermedad;
    }

    public void setEnfermedad(Enfermedad enf) {
        this.enfermedad = enf;
    }
}
