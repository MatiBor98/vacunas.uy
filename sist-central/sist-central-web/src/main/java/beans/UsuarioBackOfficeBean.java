package beans;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import datos.dtos.UsuarioBackOfficeDTO;
import datos.entidades.Enfermedad;
import datos.exceptions.EmailRegistradoException;
import logica.servicios.local.UsuariosBackOfficeBeanLocal;

@Named("UsuarioBackOfficeBean")
@RequestScoped
public class UsuarioBackOfficeBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	UsuariosBackOfficeBeanLocal usuarios;

	private String email;
	private String password = null;
	private String password2 = null;
	private String rol;
	private String emailInvalido = "none";
	private String passwordInvalido = "none";
	private String usuarioAgregado = "none";
	private String usuarioYaExiste = "none";
	private String elegirRol = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	
	public UsuarioBackOfficeBean() {
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
	
	public void agregarUsuario() {
		if(rol == null || rol.equals("")) {
			this.setElegirRol("block");
			this.setPasswordInvalido("none");
			this.setEmailInvalido("none");
			this.setUsuarioYaExiste("none");
			this.setUsuarioAgregado("none");
		} else if (!password.equals(password2)) {
			this.setPasswordInvalido("block");
			this.setElegirRol("none");
			this.setEmailInvalido("none");
			this.setUsuarioYaExiste("none");
			this.setUsuarioAgregado("none");
		} else {
			Pattern pattern = Pattern.compile(".+@.+", Pattern.CASE_INSENSITIVE);
			Matcher match = pattern.matcher(email);
			boolean matchNombre = match.find();
			if (!matchNombre) {
				this.setEmailInvalido("block");
				this.setPasswordInvalido("none");
				this.setElegirRol("none");
				this.setUsuarioYaExiste("none");
				this.setUsuarioAgregado("none");
			} else {
				try {
					usuarios.addBOUser(email, password, rol);
					this.setUsuarioAgregado("block");
					this.setUsuarioYaExiste("none");
					this.setEmailInvalido("none");
					this.setPasswordInvalido("none");
					this.setElegirRol("none");
					this.setPassword(null);
					this.setPassword2(null);
					this.setEmail(null);
					this.setRol(null);
				} catch (EmailRegistradoException e) {
					this.setUsuarioYaExiste("block");
					this.setEmailInvalido("none");
					this.setPasswordInvalido("none");
					this.setElegirRol("none");
					this.setUsuarioAgregado("none");
				}
			}

		}
	}
	
	public List<UsuarioBackOfficeDTO> listarUsuarios(){
		return usuarios.usersList();
	}


	public String getPassword2() {
		return password2;
	}


	public void setPassword2(String password2) {
		this.password2 = password2;
	}


	public String getEmailInvalido() {
		return emailInvalido;
	}


	public void setEmailInvalido(String emailInvalido) {
		this.emailInvalido = emailInvalido;
	}


	public String getPasswordInvalido() {
		return passwordInvalido;
	}


	public void setPasswordInvalido(String passwordInvalido) {
		this.passwordInvalido = passwordInvalido;
	}


	public String getUsuarioAgregado() {
		return usuarioAgregado;
	}


	public void setUsuarioAgregado(String usuarioAgregado) {
		this.usuarioAgregado = usuarioAgregado;
	}


	public String getUsuarioYaExiste() {
		return usuarioYaExiste;
	}


	public void setUsuarioYaExiste(String usuarioYaExiste) {
		this.usuarioYaExiste = usuarioYaExiste;
	}


	public String getElegirRol() {
		return elegirRol;
	}


	public void setElegirRol(String elegirRol) {
		this.elegirRol = elegirRol;
	}
}
