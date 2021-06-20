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

@Entity
@DiscriminatorValue("Vacunador")
public class Vacunador extends Ciudadano implements Serializable{

	private static final long serialVersionUID = 1L;
	/*@OneToMany
	private List<Mensaje> mensajesEnviados;*/

	/*public List<Mensaje> getMensajesEnviados() {
		return mensajesEnviados;
	}

	public void setMensajesEnviados(List<Mensaje> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}*/



	public Vacunador() { }

	public Vacunador(int ci, String email, String nombre) {
		super();
		this.ci = ci;
		this.email = email;
		this.nombre = nombre;
		this.reservas = new HashMap<String, Reserva>();
	}

	
	
	
	
}