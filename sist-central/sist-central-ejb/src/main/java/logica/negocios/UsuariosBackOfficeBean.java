package logica.negocios;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;

import datos.dtos.SessionTokens;
import datos.dtos.UsuarioBackOfficeDTO;
import datos.entidades.Administrador;
import datos.entidades.Autoridad;
import datos.entidades.UsuarioBO;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.EmailRegistradoException;
import datos.exceptions.PasswordIncorrectaException;
import datos.repositorios.UsuariosBackOfficeRepositoryLocal;
import io.jsonwebtoken.Jwts;
import logica.creacion.Converter;
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
	
	@EJB
	KeyStore keyStore;
	
    /**
     * Default constructor. 
     */	
    public UsuariosBackOfficeBean() {
        // TODO Auto-generated constructor stub
    }

    @PostConstruct
    public void  init() {
    	try {
    		if(usuariosBO.find("admin") == null) 
    			this.addBOUser("admin", "admin", "Administrador");
    		
		} catch (EmailRegistradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public SessionTokens auntenticarUsuario(String email, String password) throws EmailNoRegistradoException, PasswordIncorrectaException{
    	
    	UsuarioBO usuario = usuariosBO.find(email);
    	if(usuario == null) {
    		throw new EmailNoRegistradoException();
    	}
    	if(!checkPass(password,usuario.getPassword())){
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
    			.signWith(keyStore.getKey()).compact();
    	
    	String jwtRefresh = Jwts.builder()
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp+1000 * 60 * 60 * 16))
				.claim("email", usuario.getEmail())
    			.claim("rol", rol)
				.signWith(keyStore.getKey()).compact();
    	
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
    	nuevoUsuario.setPassword(hashPassword(password));
    	usuariosBO.save(nuevoUsuario);
    }
    
    public List<UsuarioBackOfficeDTO> usersList(){
        return usuariosBO.find().parallelStream().map(usuarioBackOfficeConverter::convert).collect(Collectors.toList());

    }
    
    public void overwriteUsuarioBackOffice(UsuarioBackOfficeDTO newUser) {
    	UsuarioBO userLegacy = usuariosBO.find(newUser.getEmail());
    	if(userLegacy instanceof Administrador && (newUser.getRol().equals("autoridad"))) {
    		usuariosBO.AdministradorToAutoridad(userLegacy.getEmail());
    	}
    	else if(userLegacy instanceof Autoridad && (newUser.getRol().equals("administrador"))) {
    		usuariosBO.AutoridadToAdministrador(userLegacy.getEmail());
    	}
    }
    
    private String hashPassword(String plainPassword) {
    	return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    
    private boolean checkPass(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
