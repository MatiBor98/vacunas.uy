package datos.dtos;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TurnoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nombre;
	//horas de inicio y fin puestas como string por problemas de serializacion
	private String inicio; 
	private String fin;
	private String vacunatorio;
	private List<AsignacionDTO> asignaciones;
	
	public TurnoDTO() {}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getVacunatorio() {
		return vacunatorio;
	}

	public void setVacunatorio(String vacunatorio) {
		this.vacunatorio = vacunatorio;
	}

	public List<AsignacionDTO> getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(List<AsignacionDTO> asignaciones) {
		this.asignaciones = asignaciones;
	}

	public TurnoDTO(String nombre, String inicio, String fin, String vacunatorio) {
		super();
		this.nombre = nombre;
		this.inicio = inicio;
		this.fin = fin;
		this.vacunatorio = vacunatorio;
		this.asignaciones = new ArrayList<AsignacionDTO>();
	}
	
	
	
}
