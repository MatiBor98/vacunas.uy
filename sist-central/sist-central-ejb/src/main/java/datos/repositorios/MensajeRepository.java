package datos.repositorios;

import datos.entidades.Mensaje;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Session Bean implementation class ChatRepository
 */
@Singleton
@LocalBean
public class MensajeRepository implements MensajeRepositoryLocal {

	@PersistenceContext(unitName = "sist-centralPersistenceUnit")
	EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public MensajeRepository() {
        // TODO Auto-generated constructor stub
    }

    public void addMensaje(Mensaje msg) {
    	entityManager.persist(msg);
    }
    
    public List<Mensaje> findMensaje() {
    	return entityManager.createQuery("select m from Mensaje m").getResultList();
    }
    
}
