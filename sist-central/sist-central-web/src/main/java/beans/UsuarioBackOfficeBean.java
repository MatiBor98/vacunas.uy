package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import datos.dtos.UsuarioBackOfficeDTO;
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
	private String busqueda;
	private boolean realizarBusqueda;
	private String hayBusqueda="none";
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
		List<UsuarioBackOfficeDTO> res = new ArrayList<>();
		List<UsuarioBackOfficeDTO> usus = usuarios.usersList();
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (UsuarioBackOfficeDTO usu : usus) {
				Matcher match1 = pattern.matcher(usu.getEmail());
				boolean matchNombre = match1.find();
				if (matchNombre) {
					res.add(usu);
				}
			}
		} else {
			res = usus;
		}
		return res;
	}

	public void overwriteUsuario(String email, String rol) {
		UsuarioBackOfficeDTO user = new UsuarioBackOfficeDTO(email, rol);
		usuarios.overwriteUsuarioBackOffice(user);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Usuario modificado con exito"));
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
	
	public boolean esAdministrador(UsuarioBackOfficeDTO user) {
		return user.getRol().equals("administrador");
	}
	
	public String getBusqueda() {
		return busqueda;
	}
	
	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}
	
	public Boolean getRealizarBusqueda() {
		return realizarBusqueda;
	}
	
	public void setRealizarBusqueda(Boolean realizarBusqueda) {
		this.realizarBusqueda = realizarBusqueda;
		if ((this.busqueda != null) && (!this.busqueda.equals(""))) {
			this.hayBusqueda = "block";
		} else {
			this.hayBusqueda = "none";
		}
	}
	
	public String getHayBusqueda() {
		return hayBusqueda;
	}
	
	public void setHayBusqueda(String hayBusqueda) {
		this.hayBusqueda = hayBusqueda;
	}
}
