package beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import datos.dtos.CiudadanoDTO;
import datos.exceptions.CiudadanoNoEncontradoException;
import logica.servicios.local.CiudadanoServiceLocal;

@Named
@SessionScoped
public class ConsultaUsuarioFrontOfficeBean implements Serializable {

	@EJB
	CiudadanoServiceLocal usuarios;
	
	private static final long serialVersionUID = 1L;
	
	private int consultaUsuario;
	private static int consultaUsuarioStatic;
	
	private String email;
	private String nombre;
	private boolean vacunador;

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

	public int getConsultaUsuario() {
		return consultaUsuario;
	}

	public void setConsultaUsuario(int usu) {
		this.consultaUsuario = usu;
		this.consultaUsuarioStatic = consultaUsuario;
		try {
			CiudadanoDTO ciudadano =  usuarios.findByNombreCi(consultaUsuarioStatic);
			email = ciudadano.getEmail();
			nombre = ciudadano.getNombre();
			vacunador = ciudadano.getVacunador();
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ConsultaUsuarioFrontOffice.xhtml");
		} catch (CiudadanoNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static int getConsultaUsuarioStatic() {
		return consultaUsuarioStatic;
	}
	
	public void overwriteCiudadano() {
		CiudadanoDTO ciudadanoDTO = new CiudadanoDTO(consultaUsuarioStatic, nombre, email, vacunador);
		usuarios.overwriteCiudadano(ciudadanoDTO);
	}

}
