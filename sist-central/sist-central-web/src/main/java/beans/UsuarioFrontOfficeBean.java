package beans;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import datos.dtos.CiudadanoDTO;
import datos.dtos.UsuarioBackOfficeDTO;
import datos.entidades.Ciudadano;
import datos.exceptions.CiudadanoRegistradoException;
import logica.servicios.local.CiudadanoServiceLocal;

@Named
@SessionScoped
public class UsuarioFrontOfficeBean implements Serializable {

	@EJB
	CiudadanoServiceLocal usuarios;
	
	private String color = "white";
	private String colorSecundario = "#222938";
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
	
	public List<CiudadanoDTO> listarUsuarios(){
		return usuarios.find();
	}

	public String getColor() {
		if (this.color.equals("white")) {
			this.color = "#222938";
			this.colorSecundario = "white";
		} else {
			this.color = "white";
			this.colorSecundario = "#222938";
		}
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getColorSecundario() {
		return colorSecundario;
	}
	
	public void setColorSecundario(String colorSecundario) {
		this.colorSecundario = colorSecundario;
	}
	
}
