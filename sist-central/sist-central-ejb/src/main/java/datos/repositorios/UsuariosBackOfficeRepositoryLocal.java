package datos.repositorios;

import datos.entidades.UsuarioBO;
import datos.exceptions.EmailRegistradoException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UsuariosBackOfficeRepositoryLocal {
	
    public UsuarioBO find(String email);
    
    public void save(UsuarioBO usuario) throws EmailRegistradoException;
    
    public List<UsuarioBO> find();

	public void AdministradorToAutoridad(String email);

	public void AutoridadToAdministrador(String email);

}
