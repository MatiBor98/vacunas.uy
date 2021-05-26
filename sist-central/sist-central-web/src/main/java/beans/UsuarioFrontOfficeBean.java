package beans;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import datos.dtos.CiudadanoDTO;
import datos.dtos.UsuarioBackOfficeDTO;
import datos.entidades.Ciudadano;
import logica.servicios.local.CiudadanoServiceLocal;

@Named
@SessionScoped
public class UsuarioFrontOfficeBean implements Serializable {

	private String color = "white";
	private String colorSecundario = "#222938";

	
	@EJB
	CiudadanoServiceLocal usuarios;
	
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
