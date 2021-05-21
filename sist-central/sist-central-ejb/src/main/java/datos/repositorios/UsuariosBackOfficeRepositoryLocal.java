package datos.repositorios;

import java.util.List;

import javax.ejb.Local;

import datos.entidades.UsuarioBO;
import datos.exceptions.EmailRegistradoException;

@Local
public interface UsuariosBackOfficeRepositoryLocal {
	
    public UsuarioBO find(String email);
    
    public void save(UsuarioBO usuario) throws EmailRegistradoException;
    
    public List<UsuarioBO> find();

}
