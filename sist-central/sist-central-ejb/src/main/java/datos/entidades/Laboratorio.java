package datos.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class Laboratorio implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String nombre;
	private Map<String, Vacuna> vacunas;

	
	public Laboratorio() {
		
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Map<String, Vacuna> getVacunas() {
		return vacunas;
	}
	public void setVacunas(Map<String, Vacuna> vacs) {
		this.vacunas = vacs;
	}
}
