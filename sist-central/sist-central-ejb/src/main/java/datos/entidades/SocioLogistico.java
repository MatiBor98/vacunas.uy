package datos.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SocioLogistico implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String nombre;

	private boolean habilitado;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public SocioLogistico(String nombre, boolean habilitado) {
		super();
		this.nombre = nombre;
		this.habilitado = habilitado;
	}

	public SocioLogistico() {
		super();
	}


	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
}
