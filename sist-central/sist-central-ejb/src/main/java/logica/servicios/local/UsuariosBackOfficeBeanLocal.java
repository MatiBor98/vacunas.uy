package logica.servicios.local;

import javax.ejb.Local;

import datos.entidades.UsuarioBO;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.EmailRegistradoException;
import datos.exceptions.PasswordIncorrectaException;

@Local
public interface UsuariosBackOfficeBeanLocal {

    public String auntenticarUsuario(String email, String password) throws EmailNoRegistradoException, PasswordIncorrectaException;
    public void addBOUser(String email, String password, String rol) throws EmailRegistradoException;
}
