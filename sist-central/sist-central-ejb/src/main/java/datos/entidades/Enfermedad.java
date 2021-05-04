package datos.entidades;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Enfermedad implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String nombre;
	private String descripcion;
	private Map<String, Vacuna> vacunas;
	private Map<String, PlanVacunacion> planesVac;
	
	public Enfermedad() {
		
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Map<String, Vacuna> getVacunas() {
		return this.vacunas;
	}
	public void setVacunas(Map<String, Vacuna> vacs) {
		this.vacunas = vacs;
	}
	public Map<String, PlanVacunacion> getPlanesVac() {
		return this.planesVac;
	}
	public void setPlanesVac(Map<String, PlanVacunacion> pVac) {
		this.planesVac = pVac;
	}
	public String getDescripcion() {
		return this.descripcion;
	}
	public void setDescripcion(String desc) {
		this.descripcion = desc;
	}
}
