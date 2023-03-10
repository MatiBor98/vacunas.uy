package datos.entidades;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PuestoVacunacion {
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaAgendaId")
	@SequenceGenerator(name="puestoId",sequenceName="puestoId", allocationSize=1)
	private int id;

	@ManyToOne
	@JoinColumn(name="vacunatorioNombre", nullable=false)
	private Vacunatorio vacunatorio;

	private String nombrePuesto;
	
	@OneToMany(mappedBy="puestoVacunacion")
	@LazyCollection(LazyCollectionOption.FALSE)
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Asignacion> getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(List<Asignacion> asignaciones) {
		this.asignaciones = asignaciones;
	}
}
