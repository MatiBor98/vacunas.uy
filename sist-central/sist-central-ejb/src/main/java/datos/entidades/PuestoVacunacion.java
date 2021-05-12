package datos.entidades;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PuestoVacunacion {
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaAgendaId")
	@SequenceGenerator(name="puestoId",sequenceName="puestoId", allocationSize=1)
	private long id;

	@ManyToOne
	@JoinColumn(name="vacunatorioId", nullable=false)
	private Vacunatorio vacunatorio;

	private String nombrePuesto;
	
	@OneToMany(mappedBy="puestoVacunacion")
	private List<Asignacion> asignaciones;

	public Vacunatorio getVacunatorio() {
		return vacunatorio;
	}

	public void setVacunatorio(Vacunatorio vacunatorio) {
		this.vacunatorio = vacunatorio;
	}

	public String getNombrePuesto() {
		return nombrePuesto;
	}

	public void setNombrePuesto(String nombrePuesto) {
		this.nombrePuesto = nombrePuesto;
	}

	public PuestoVacunacion(Vacunatorio vacunatorio, String nombrePuesto) {
		super();
		this.vacunatorio = vacunatorio;
		this.nombrePuesto = nombrePuesto;
		this.asignaciones = new ArrayList<Asignacion>();
	}

	public PuestoVacunacion() {
		super();
		this.asignaciones = new ArrayList<Asignacion>();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Asignacion> getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(List<Asignacion> asignaciones) {
		this.asignaciones = asignaciones;
	}
}
