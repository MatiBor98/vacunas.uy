package logica.negocios;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import datos.dtos.SessionTokens;
import datos.dtos.UsuarioBackOfficeDTO;
import datos.entidades.Administrador;
import datos.entidades.Autoridad;
import datos.entidades.Ciudadano;
import datos.entidades.UsuarioBO;
import datos.entidades.Vacunador;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.EmailRegistradoException;
import datos.exceptions.PasswordIncorrectaException;
import datos.repositorios.UsuariosBackOfficeRepositoryLocal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import logica.creacion.Converter;
import logica.creacion.UsuarioBackOfficeConverter;
import logica.servicios.local.UsuariosBackOfficeBeanLocal;

/**
 * Session Bean implementation class AutenticacionBackOfficeBean
 */
@Stateless
public class UsuariosBackOfficeBean implements UsuariosBackOfficeBeanLocal {

	@EJB
	UsuariosBackOfficeRepositoryLocal usuariosBO;
	
	@Inject
	private Converter<UsuarioBO, UsuarioBackOfficeDTO> usuarioBackOfficeConverter;
	
	String password = "secretKeyforJwt.secretKeyForJwt.secretKeyforJwt";
	SecretKey key = Keys.hmacShaKeyFor(password.getBytes(StandardCharsets.UTF_8));
	
	
    /**
     * Default constructor. 
     */	
    public UsuariosBackOfficeBean() {
        // TODO Auto-generated constructor stub
    }

    public SessionTokens auntenticarUsuario(String email, String password) throws EmailNoRegistradoException, PasswordIncorrectaException{
    	
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
    			.setExpiration(new Date(timestamp+ 1000 * 60 * 10))
    			.claim("email", usuario.getEmail())
    			.claim("rol", rol)
    			.signWith(key).compact();
    	
    	String jwtRefresh = Jwts.builder()
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp+1000 * 60 * 60 * 16))
				.claim("email", usuario.getEmail())
    			.claim("rol", rol)
				.signWith(key).compact();
    	
    	SessionTokens token = new SessionTokens();
    	token.setAccessToken(jwt);
    	token.setRefreshToken(jwtRefresh);
    	
    	return token;
    }
    
    public void addBOUser(String email, String password, String rol) throws EmailRegistradoException {
    	UsuarioBO nuevoUsuario;
    	if(rol.equals("Administrador")) {
    		nuevoUsuario = new Administrador();
    	}
    	else{
    		nuevoUsuario = new Autoridad();
    	}
    	nuevoUsuario.setEmail(email);
    	nuevoUsuario.setPassword(password);
    	usuariosBO.save(nuevoUsuario);
    }
    
    public List<UsuarioBackOfficeDTO> usersList(){
        return usuariosBO.find().parallelStream().map(usuarioBackOfficeConverter::convert).collect(Collectors.toList());

    }
    
    public void overwriteUsuarioBackOffice(UsuarioBackOfficeDTO newUser) {
    	UsuarioBO userLegacy = usuariosBO.find(newUser.getEmail());
    	if(!(userLegacy instanceof Administrador) && (newUser.getRol().equals("autoridad"))) {
    		usuariosBO.AdministradorToAutoridad(userLegacy.getEmail());
    	}
    	else if(userLegacy instanceof Autoridad && (newUser.getRol().equals("administrador"))) {
    		usuariosBO.AutoridadToAdministrador(userLegacy.getEmail());
    	}
    }
}
