package datos.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
public class Enfermedad implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String nombre;
	private String descripcion;
	@ManyToMany(mappedBy = "enfermedades")
	@JoinColumn(name="vacunaNombre", nullable=false)
	private List<Vacuna> vacunas;
	@OneToMany(mappedBy="enfermedad")
	private List<PlanVacunacion> planesVacunacion;
	
	public Enfermedad() {
		super();
	}
	
	public Enfermedad(String nombre, String descripcion, List<Vacuna> vacunas, List<PlanVacunacion> planesVacunacion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.vacunas = vacunas;
		this.planesVacunacion = planesVacunacion;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String desc) {
		this.descripcion = desc;
	}
	
	public List<Vacuna> getVacunas() {
		return this.vacunas;
	}

	public void setVacunas(List<Vacuna> vacunas) {
		this.vacunas = vacunas;
	}
	
	public List<PlanVacunacion> getPlanesVacunacion() {
		return this.planesVacunacion;
	}

	public void setPlanesVacunacion(List<PlanVacunacion> planesVacunacion) {
		this.planesVacunacion = planesVacunacion;
	}
}
