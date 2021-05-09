package datos.entidades;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.time.LocalTime;

@Embeddable
public class Turno {
	@Id
	private String nombre;
	private LocalTime inicio;
	private LocalTime fin;

	public Turno() {}

	public LocalTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalTime inicio) {
		this.inicio = inicio;
	}

	public LocalTime getFin() {
		return fin;
	}

	public void setFin(LocalTime fin) {
		this.fin = fin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
