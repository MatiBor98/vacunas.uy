package beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.json.JSONObject;

import datos.entidades.Administrador;
import datos.entidades.Autoridad;
import datos.entidades.UsuarioBO;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.PasswordIncorrectaException;
import logica.servicios.local.AutenticacionBackOfficeBeanLocal;

@Named("LoginBO")
@SessionScoped
public class LoginBackOfficeBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	AutenticacionBackOfficeBeanLocal usuarios;

	String email = null;
	String password = null;
	private boolean wrongPassword = false;
	private boolean wrongEmail = false;
	private boolean loggedIn = false;
	private String rol = null;
	
	public LoginBackOfficeBean() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
    public void init() {
        loggedIn = false;
        wrongPassword = false;
        wrongEmail = false;
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

	public void login() {
		wrongEmail = wrongPassword = false;
		try {
			String jwt = usuarios.auntenticarUsuario(email, password);
			String[] tokenParts = jwt.split("\\.");
			Base64.Decoder decoder = Base64.getDecoder();
			String header = new String (decoder.decode(tokenParts[0]));
			String playload = new String (decoder.decode(tokenParts[1]));
			JSONObject body = new JSONObject(playload);
			if(body.getString("rol").equals("administrador")) {
				rol = "admin";
				loggedIn = true;
				FacesContext.getCurrentInstance().getExternalContext().redirect("/backoffice/Administrador.xhtml");
			}
			else if(body.get("rol").equals("autoridad")){
				rol = "auth";
				loggedIn = true;
				FacesContext.getCurrentInstance().getExternalContext().redirect("/backoffice/Autoridad.xhtml");
			}
		} catch (EmailNoRegistradoException e) {
			wrongEmail = true;
		} catch (PasswordIncorrectaException e) {
			wrongPassword = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
	
	public void logout() {
		loggedIn = false;
		wrongPassword= false;
		wrongEmail = false;
	}
}
