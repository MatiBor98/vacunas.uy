package beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import datos.exceptions.EmailRegistradoException;
import org.json.JSONObject;

import datos.dtos.SessionTokens;
import datos.entidades.Administrador;
import datos.entidades.Autoridad;
import datos.entidades.UsuarioBO;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.PasswordIncorrectaException;
import logica.servicios.local.UsuariosBackOfficeBeanLocal;

@Named("LoginBO")
@RequestScoped
public class LoginBackOfficeBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	UsuariosBackOfficeBeanLocal usuarios;

	String email = null;
	String password = null;
	boolean sesionExpirada;
	String error;
	
	public boolean isSesionExpirada() {
		return sesionExpirada;
	}

	public void setSesionExpirada(boolean sesionExpirada) {
		this.sesionExpirada = sesionExpirada;
	}

	public String getError() {
		return error;
	}
	
	public LoginBackOfficeBean() {
		// TODO Auto-generated constructor stub
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

	@PostConstruct
	public void init() {
		String error = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("error");
		if(error != null) {
			sesionExpirada = true;
		}
		
	}
	
	public void login() {
		try {
			SessionTokens tokens = usuarios.auntenticarUsuario(email, password);
			String jwt = tokens.getAccessToken();
			String refresh = tokens.getRefreshToken();
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			//ahora creamos los parametros para la cookie
			Map<String, Object> properties = new HashMap<String,Object>();
			properties.put("path", "/");
			properties.put("maxAge", 60 * 60);
			properties.put("httpOnly", true);
			//aca se agrega la cookie a la response
			ec.addResponseCookie("JWTBO", jwt, properties);
			
			Map<String, Object> propertiesRefresh = new HashMap<String,Object>();
			properties.put("path", "/");
			properties.put("maxAge", 60 * 60 * 24);
			properties.put("httpOnly", true);
			//aca se agrega la cookie a la response
			ec.addResponseCookie("JWTBORefresh", refresh, propertiesRefresh);
			
			String[] tokenParts = jwt.split("\\.");
			Base64.Decoder decoder = Base64.getDecoder();
			String header = new String (decoder.decode(tokenParts[0]));
			String playload = new String (decoder.decode(tokenParts[1]));
			JSONObject body = new JSONObject(playload);
			//ahora miramos en el playload que rol tiene para decidir a donde redirigir
			if(body.getString("rol").equals("administrador")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/backoffice/administrador/Administrador.xhtml");
			}
			else if(body.get("rol").equals("autoridad")){
				FacesContext.getCurrentInstance().getExternalContext().redirect("/backoffice/autoridad/Autoridad.xhtml");
			}
		} catch (EmailNoRegistradoException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El email ingresado no se encuentra registrado"));
		} catch (PasswordIncorrectaException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Constrasena incorrecta"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public void logout() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

		//tenemos q eliminar las cookies, pero como no existe un mecanismo para tal cosa se sobreescriben
		Map<String, Object> properties = new HashMap<String,Object>();
		properties.put("path", "/");
		properties.put("maxAge", 0);
		properties.put("httpOnly", true);
		ec.addResponseCookie("JWTBO", "", properties);
		ec.addResponseCookie("JWTBORefresh", "", properties);
		
		try {
			ec.redirect("/Login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
