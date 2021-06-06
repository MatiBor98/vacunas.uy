package datos.dtos;

import java.io.Serializable;

public class CiudadanoDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ci;
    private String nombre;
    private String email;
    private boolean vacunador;

    public boolean getVacunador() {
		return vacunador;
	}

	public void setVacunador(boolean vacunador) {
		this.vacunador = vacunador;
	}

	public CiudadanoDTO() {}

    public CiudadanoDTO(int ci, String nombre, String email, boolean vacunador) {
        this.ci = ci;
        this.nombre = nombre;
        this.email = email;
        this.vacunador = vacunador;
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

    @Override
    public String toString() {
    	if (vacunador) {
            return String.valueOf(ci) + " " + nombre + " " + email + " vacunador";
    	}
        return String.valueOf(ci) + " " + nombre + " " + email;
    }


}

