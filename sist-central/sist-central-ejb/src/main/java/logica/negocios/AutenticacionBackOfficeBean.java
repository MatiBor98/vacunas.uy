package logica.negocios;

import java.nio.charset.StandardCharsets;
import java.sql.Date;

import javax.crypto.SecretKey;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import datos.entidades.Administrador;
import datos.entidades.UsuarioBO;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.PasswordIncorrectaException;
import datos.repositorios.UsuariosBackOfficeRepositoryLocal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import logica.servicios.local.AutenticacionBackOfficeBeanLocal;

/**
 * Session Bean implementation class AutenticacionBackOfficeBean
 */
@Stateless
public class AutenticacionBackOfficeBean implements AutenticacionBackOfficeBeanLocal {

	@EJB
	UsuariosBackOfficeRepositoryLocal usuariosBO;
	
	
	String password = "secretKeyforJwt.secretKeyForJwt.secretKeyforJwt";
	SecretKey key = Keys.hmacShaKeyFor(password.getBytes(StandardCharsets.UTF_8));
	
	
    /**
     * Default constructor. 
     */	
    public AutenticacionBackOfficeBean() {
        // TODO Auto-generated constructor stub
    }

    public String auntenticarUsuario(String email, String password) throws EmailNoRegistradoException, PasswordIncorrectaException{
    	
    	UsuarioBO usuario = usuariosBO.find(email);
    	if(usuario == null) {
    		throw new EmailNoRegistradoException();
    	}
    	if(!usuario.getPassword().equals(password)){
    		throw new PasswordIncorrectaException();
    	}
    	String rol;
    	if(usuario instanceof Administrador) {
    		rol = "administrador";
    	}
    	else {
    		rol = "autoridad";
    	}
    	long timestamp = System.currentTimeMillis();
    	String jwt = Jwts.builder()
    			.setIssuedAt(new Date(timestamp))
    			.setExpiration(new Date(timestamp+60000))
    			.claim("email", usuario.getEmail())
    			.claim("password", usuario.getPassword())
    			.claim("rol", rol)
    			.signWith(key).compact();
    	
    	return jwt;
    }
    
}
