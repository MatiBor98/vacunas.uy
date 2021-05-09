package datos.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import datos.entidades.Laboratorio;

@Entity
@DiscriminatorValue("Vacunador")
public class Vacunador extends Ciudadano implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Mensaje> mensajesEnviados;
	private Map<LocalDate, Asignacion> asignaciones; // map por fechaInicio
	public List<Mensaje> getMensajesEnviados() {
		return mensajesEnviados;
	}
	public void setMensajesEnviados(List<Mensaje> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}
	public Map<LocalDate, Asignacion> getAsignaciones() {
		return asignaciones;
	}
	public void setAsignaciones(Map<LocalDate, Asignacion> asignaciones) {
		this.asignaciones = asignaciones;
	}
	public Vacunador() {
		super();
		this.mensajesEnviados = new ArrayList<Mensaje>();
		this.asignaciones = new HashMap<LocalDate, Asignacion>();
	}

	
	
	
	
	
}