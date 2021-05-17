package datos.repositorios;

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

    public UsuarioBO find(String email) {
    	return entityManager.find(UsuarioBO.class, email);
    }
    
    public void save(UsuarioBO usuario) throws EmailRegistradoException {
    	try {
        	entityManager.persist(usuario);
    	}
    	catch(EntityExistsException e) {
    		throw new EmailRegistradoException();
    	}
    }
}
