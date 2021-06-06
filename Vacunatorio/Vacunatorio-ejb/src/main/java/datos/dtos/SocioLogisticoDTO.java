package datos.dtos;

import java.io.Serializable;


public class SocioLogisticoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nombre;

	private boolean habilitado;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public SocioLogisticoDTO(String nombre, boolean habilitado) {
		super();
		this.nombre = nombre;
		this.habilitado = habilitado;
	}

	public SocioLogisticoDTO() {
		super();
	}


	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
}
