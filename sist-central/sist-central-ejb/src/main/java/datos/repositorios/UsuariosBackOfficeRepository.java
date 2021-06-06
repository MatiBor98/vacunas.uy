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
    
    @PostConstruct
    public void  init() {
    	Administrador usr = new Administrador();
    	usr.setEmail("admin");
    	usr.setPassword("admin");
    	try {
			this.save(usr);
		} catch (EmailRegistradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	    	entityManager.createNativeQuery("UPDATE usuarioBO "
	    										+ "SET TipoDeUsuario = 'autoridad' "
	    											+ "WHERE email = " + email + ";").executeUpdate();
	}

	@Override
	public void AutoridadToAdministrador(String email) {
		entityManager.createNativeQuery("UPDATE usuarioBO "
				+ "SET TipoDeUsuario = 'administrador' "
					+ "WHERE email = " + email + ";").executeUpdate();
	}
	

}
