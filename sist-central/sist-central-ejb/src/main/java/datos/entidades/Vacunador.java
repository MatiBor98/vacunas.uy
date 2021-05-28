package datos.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import datos.entidades.Laboratorio;

@Entity
@DiscriminatorValue("Vacunador")
public class Vacunador extends Ciudadano implements Serializable{

	private static final long serialVersionUID = 1L;
	/*@OneToMany
	private List<Mensaje> mensajesEnviados;*/
	@OneToMany(mappedBy = "vacunador")
	@MapKey(name = "fechaInicio")
	private Map<LocalDate, Asignacion> asignaciones;

	/*public List<Mensaje> getMensajesEnviados() {
		return mensajesEnviados;
	}

	public void setMensajesEnviados(List<Mensaje> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}*/

	public Map<LocalDate, Asignacion> getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(Map<LocalDate, Asignacion> asignaciones) {
		this.asignaciones = asignaciones;
	}
	
	public void addAsignacion(LocalDate fecha, Asignacion asignacion) {
		asignaciones.put(fecha, asignacion);
	}

	public Vacunador() { }

	public Vacunador(int ci, String email, String nombre) {
		super();
		this.ci = ci;
		this.email = email;
		this.nombre = nombre;
		this.reservas = new HashMap<String, Reserva>();
		this.asignaciones = new HashMap<LocalDate, Asignacion>();
	}

	
	
	
	
}