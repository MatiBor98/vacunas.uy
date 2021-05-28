package datos.entidades;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;



@Entity
public class PlanVacunacion implements Serializable {
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private String nombre;

    private LocalDate inicio;

    private LocalDate fin;

    @ManyToOne
    @JoinColumn(name="enfermedadNombre", nullable=false)
    private Enfermedad enfermedad;
    
    @OneToMany(mappedBy="planVacunacion")
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<Etapa> etapas;

    public PlanVacunacion() {
    }

    public PlanVacunacion(String nombre, LocalDate inicio, LocalDate fin, Enfermedad enfermedad) {
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
        this.enfermedad = enfermedad;
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
    
    public List<Etapa> getEtapas() {
        return this.etapas;
    }

    public void setEtapas(List<Etapa> ets) {
        this.etapas = ets;
    }
    
    
}
