package logica.negocios;

import javax.ejb.Stateless;

import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.PasswordIncorrectaException;
import logica.servicios.local.AutenticacionBackOfficeBeanLocal;

/**
 * Session Bean implementation class AutenticacionBackOfficeBean
 */
@Stateless
public class AutenticacionBackOfficeBean implements AutenticacionBackOfficeBeanLocal {

    /**
     * Default constructor. 
     */	
    public AutenticacionBackOfficeBean() {
        // TODO Auto-generated constructor stub
    }

    public auntenticarUsuario(String email, String password) throws EmailNoRegistradoException, PasswordIncorrectaException{
    	
    }
}
