package datos.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Map;

@Entity
public class Laboratorio implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String nombre;

	public Laboratorio() {}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
