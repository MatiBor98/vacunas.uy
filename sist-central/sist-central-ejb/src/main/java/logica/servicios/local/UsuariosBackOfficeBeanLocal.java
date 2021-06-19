package logica.servicios.local;

import datos.dtos.SessionTokens;
import datos.dtos.UsuarioBackOfficeDTO;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.EmailRegistradoException;
import datos.exceptions.PasswordIncorrectaException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UsuariosBackOfficeBeanLocal {

    public SessionTokens auntenticarUsuario(String email, String password) throws EmailNoRegistradoException, PasswordIncorrectaException;
    public void addBOUser(String email, String password, String rol) throws EmailRegistradoException;
    public List<UsuarioBackOfficeDTO> usersList();
    public void overwriteUsuarioBackOffice(UsuarioBackOfficeDTO newUser);

    
}
