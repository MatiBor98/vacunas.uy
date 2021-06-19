package datos.dtos;

import java.io.Serializable;
import java.util.List;

public class EnfermedadDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String descripcion;
	private List<VacunaDTO> vacunas;
	private List<PlanVacunacionDTO> planesVacunacion;
	
	public EnfermedadDTO() {
		super();
	}
	
	public EnfermedadDTO(String nombre, String descripcion, List<VacunaDTO> vacunas, List<PlanVacunacionDTO> planesVacunacion) {
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
	
	public List<VacunaDTO> getVacunas() {
		return this.vacunas;
	}

	public void setVacunas(List<VacunaDTO> vacunas) {
		this.vacunas = vacunas;
	}
	
	public List<PlanVacunacionDTO> getPlanesVacunacion() {
		return this.planesVacunacion;
	}

	public void setPlanesVacunacion(List<PlanVacunacionDTO> planesVacunacion) {
		this.planesVacunacion = planesVacunacion;
	}
}
