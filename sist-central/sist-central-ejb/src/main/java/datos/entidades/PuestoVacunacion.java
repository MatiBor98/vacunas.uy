package datos.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class PuestoVacunacion {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaAgendaId")
	@SequenceGenerator(name="puestoId",sequenceName="puestoId", allocationSize=1)
	private long id;
	
	private Vacunatorio vacunatorio;
	private Asignacion asignacion;
	
	private String nombrePuesto;

	public Vacunatorio getVacunatorio() {
		return vacunatorio;
	}

	public void setVacunatorio(Vacunatorio vacunatorio) {
		this.vacunatorio = vacunatorio;
	}

	public Asignacion getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}

	public String getNombrePuesto() {
		return nombrePuesto;
	}

	public void setNombrePuesto(String nombrePuesto) {
		this.nombrePuesto = nombrePuesto;
	}

	public PuestoVacunacion(Vacunatorio vacunatorio, Asignacion asignacion, String nombrePuesto) {
		super();
		this.vacunatorio = vacunatorio;
		this.asignacion = asignacion;
		this.nombrePuesto = nombrePuesto;
	}

	public PuestoVacunacion() {
		super();
	}
	
	
}
