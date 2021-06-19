package datos.entidades;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Etapa implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaEtapaId")
    @SequenceGenerator(name="sequenciaEtapaId",sequenceName="sequenciaEtapaId", allocationSize=1)
    private int id;


    private String descripcion;

    private LocalDate inicio;

    private LocalDate fin;

	private String vacuna;

    @ManyToOne
    @JoinColumn(name="planNombre", nullable=false)
    private PlanVacunacion planVacunacion;

    public Etapa() { }

    public Etapa(int id, String descripcion, LocalDate inicio, LocalDate fin, String vacuna, PlanVacunacion planVacunacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.inicio = inicio;
        this.fin = fin;
        this.vacuna = vacuna;
        this.planVacunacion = planVacunacion;
    }
    
    public Etapa(String descripcion, LocalDate inicio, LocalDate fin, String vacuna, PlanVacunacion planVacunacion) {
        this.descripcion = descripcion;
        this.inicio = inicio;
        this.fin = fin;
        this.vacuna = vacuna;
        this.planVacunacion = planVacunacion;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	public String getVacuna(){
		return vacuna;
	}
	
	public void setVacuna(String vacuna){
		this.vacuna = vacuna;
	} 

    public PlanVacunacion getPlanVacunacion() {
        return planVacunacion;
    }

    public void setPlanVacunacion(PlanVacunacion planVacunacion) {
        this.planVacunacion = planVacunacion;
    }
}
