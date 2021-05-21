package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import datos.dtos.UsuarioBackOfficeDTO;
import datos.exceptions.EmailRegistradoException;
import logica.servicios.local.UsuariosBackOfficeBeanLocal;

@Named
@RequestScoped
public class UsuarioBackOfficeBean {
	
	@EJB
	UsuariosBackOfficeBeanLocal usuarios;

	private String email;
	private String password = null;
	private String rol;
	private boolean emailRegistrado = false;
	
	public UsuarioBackOfficeBean() {
		email = null;
		password = null;
		rol = null;
		emailRegistrado = false;
	}

	
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEmailRegistrado() {
		return emailRegistrado;
	}

	public void setEmailRegistrado(boolean emailRegistrado) {
		this.emailRegistrado = emailRegistrado;
	}
	
	public void registrarUsuario() {
		emailRegistrado = false;
		try {
			usuarios.addBOUser(email, password, rol);
		} catch (EmailRegistradoException e) {
			emailRegistrado = true;
		}
	}
	
	public List<UsuarioBackOfficeDTO> listarUsuarios(){
		return usuarios.usersList();
	}
}
