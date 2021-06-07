package datos.entidades;


import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Rol", 
  discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Ciudadano")
public class Ciudadano {

	@Id
	protected int ci;
	protected String nombre;
	protected String email;
	protected String firebaseTokenMovil;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ciudadano")
	@MapKey(name = "codigo")
	protected Map<String, Reserva> reservas;
	
	public Ciudadano() {}

	public Ciudadano(int ci, String nombre, String email) {
		this.ci = ci;
		this.nombre = nombre;
		this.email = email;
		this.reservas = new HashMap<String, Reserva>();
	}

	public int getCi() {
		return ci;
	}

	public void setCi(int ci) {
		this.ci = ci;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void addReserva(String codigo, Reserva reserva) {
		reservas.put(codigo, reserva);
	}
	public String getFirebaseTokenMovil() {
		return firebaseTokenMovil;
	}

	public void setFirebaseTokenMovil(String firebaseTokenMovil) {
		this.firebaseTokenMovil = firebaseTokenMovil;
	}

	public Map<String, Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Map<String, Reserva> reservas) {
		this.reservas = reservas;
	}
}
