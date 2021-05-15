package logica.negocios;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datos.entidades.UsuarioBO;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.PasswordIncorrectaException;
import datos.repositorios.UsuariosBackOfficeRepositoryLocal;
import logica.servicios.local.AutenticacionBackOfficeBeanLocal;

/**
 * Session Bean implementation class AutenticacionBackOfficeBean
 */
@Stateless
public class AutenticacionBackOfficeBean implements AutenticacionBackOfficeBeanLocal {

	@EJB
	UsuariosBackOfficeRepositoryLocal usuariosBO;
	
    /**
     * Default constructor. 
     */	
    public AutenticacionBackOfficeBean() {
        // TODO Auto-generated constructor stub
    }

    public UsuarioBO auntenticarUsuario(String email, String password) throws EmailNoRegistradoException, PasswordIncorrectaException{
    	
    	UsuarioBO usuario = usuariosBO.find(email);
    	if(usuario == null) {
    		throw new EmailNoRegistradoException();
    	}
    	if(!usuario.getPassword().equals(password)){
    		throw new PasswordIncorrectaException();
    	}
    	return usuario;
    }
    
}
