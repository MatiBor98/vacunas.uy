package beans;

import datos.dtos.CiudadanoDTO;
import datos.exceptions.CiudadanoRegistradoException;
import logica.servicios.local.CiudadanoServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UsuarioFrontOfficeBean implements Serializable {

	@EJB
	CiudadanoServiceLocal usuarios;
	
	private int ci;
	private String email;
	private String nombre;
	private boolean vacunador;
	
	public int getCi() {
		return ci;
	}

	public void setCi(int ci) {
		this.ci = ci;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isVacunador() {
		return vacunador;
	}

	public void setVacunador(boolean vacunador) {
		this.vacunador = vacunador;
	}

	public void registrarUsuario() {
		CiudadanoDTO ciudadanoDTO = new CiudadanoDTO(ci, nombre, email, vacunador);
		try {
			usuarios.save(ciudadanoDTO);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Ciudadano registrado con exito"));
		}
		catch(CiudadanoRegistradoException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ya existe un ciudadano registrado con la CI ingresada"));
		}
	}
	
	public UsuarioFrontOfficeBean() {
		// TODO Auto-generated constructor stub
	}
	
	
}
