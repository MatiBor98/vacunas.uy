package datos.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class VacunadorDTO extends CiudadanoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Map<Date, AsignacionDTO> asignaciones;
	public Map<Date, AsignacionDTO> getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(Map<Date, AsignacionDTO> asignaciones) {
		this.asignaciones = asignaciones;
	}
	
	public void addAsignacion(Date fecha, AsignacionDTO asignacion) {
		asignaciones.put(fecha, asignacion);
	}

	public VacunadorDTO() { }

	public VacunadorDTO(int ci, String email, String nombre) {
		super();
		this.setCi(ci);
		this.setEmail(email);
		this.setNombre(nombre);
		this.asignaciones = new HashMap<Date, AsignacionDTO>();
	}

	
	
	
	
}