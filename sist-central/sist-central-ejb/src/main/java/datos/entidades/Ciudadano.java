package datos.entidades;


import java.util.Map;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Rol", 
  discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Ciudadano")
public class Ciudadano {

	@Id
	private String ci;
	private String nombre;
	private String email;

	@OneToMany(cascade = CascadeType.ALL)
	@MapKey(name = "codigo")
	private Map<String, Reserva> reservas;
	
	public Ciudadano() {}

	public Ciudadano(String ci, String nombre, String email) {
		this.ci = ci;
		this.nombre = nombre;
		this.email = email;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
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


}
