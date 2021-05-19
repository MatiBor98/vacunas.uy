package logica.servicios.local;

import javax.ejb.Local;

import datos.entidades.UsuarioBO;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.PasswordIncorrectaException;

@Local
public interface AutenticacionBackOfficeBeanLocal {

    public String auntenticarUsuario(String email, String password) throws EmailNoRegistradoException, PasswordIncorrectaException;

}
