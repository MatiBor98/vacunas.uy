package datos.repositorios;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import datos.entidades.Administrador;
import datos.entidades.UsuarioBO;
import datos.exceptions.EmailRegistradoException;

/**
 * Session Bean implementation class UsuariosBackOfficeRepository
 */
@Singleton
public class UsuariosBackOfficeRepository implements UsuariosBackOfficeRepositoryLocal {

	@PersistenceContext(unitName = "sist-centralPersistenceUnit")
	private EntityManager entityManager;
	
    public UsuariosBackOfficeRepository() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<UsuarioBO> find(){
    	return entityManager.createQuery("select u from UsuarioBO u").getResultList();
    }
    
    @Override
    public UsuarioBO find(String email) {
    	return entityManager.find(UsuarioBO.class, email);
    }
    
    @Override
    public void save(UsuarioBO usuario) throws EmailRegistradoException {
    	try {
        	entityManager.persist(usuario);
    	}
    	catch(EntityExistsException e) {
    		throw new EmailRegistradoException();
    	}
    }

	@Override
	public void AdministradorToAutoridad(String email) {
	    entityManager.createNativeQuery("update usuariobo set tipodeusuario = 'autoridad' where email = '" + email + "';").executeUpdate();
	}

	@Override
	public void AutoridadToAdministrador(String email) {
		entityManager.createNativeQuery("update usuariobo set tipodeusuario = 'administrador' where email = '" + email + "';").executeUpdate();
	}
	

}
