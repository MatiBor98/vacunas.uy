package logica.servicios.local;

import java.util.List;

import javax.ejb.Local;

import datos.dtos.SessionTokens;
import datos.dtos.UsuarioBackOfficeDTO;
import datos.entidades.UsuarioBO;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.EmailRegistradoException;
import datos.exceptions.PasswordIncorrectaException;

@Local
public interface UsuariosBackOfficeBeanLocal {

    public SessionTokens auntenticarUsuario(String email, String password) throws EmailNoRegistradoException, PasswordIncorrectaException;
    public void addBOUser(String email, String password, String rol) throws EmailRegistradoException;
    public List<UsuarioBackOfficeDTO> usersList();
    
}
