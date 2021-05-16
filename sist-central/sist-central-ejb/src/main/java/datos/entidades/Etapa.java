package datos.entidades;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Etapa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaEtapaId")
    @SequenceGenerator(name="sequenciaEtapaId",sequenceName="sequenciaEtapaId", allocationSize=1)
    private int id;

    @Embedded
    private RestriccionEtapa restricciones;

    private String descripcion;

    private LocalDate inicio;

    private LocalDate fin;

	@ManyToOne
	private Vacuna vacuna;

    @ManyToOne
    @JoinColumn(name="planNombre", nullable=false)
    private PlanVacunacion planVacunacion;

    public Etapa() { }

    public Etapa(int id, RestriccionEtapa restricciones, String descripcion, LocalDate inicio, LocalDate fin, Vacuna vacuna, PlanVacunacion planVacunacion) {
        this.id = id;
        this.restricciones = restricciones;
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

    public RestriccionEtapa getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(RestriccionEtapa restricciones) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	public Vacuna getVacuna(){
		return vacuna;
	}
	
	public void setVacuna(Vacuna vacuna){
		this.vacuna = vacuna;
	} 

    public PlanVacunacion getPlanVacunacion() {
        return planVacunacion;
    }

    public void setPlanVacunacion(PlanVacunacion planVacunacion) {
        this.planVacunacion = planVacunacion;
    }
}
