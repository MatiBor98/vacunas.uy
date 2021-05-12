package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import datos.entidades.PuestoVacunacion;

/**
 * Session Bean implementation class PuestoVacunacionRepository
 */
@Singleton
@LocalBean
public class PuestoVacunacionRepository implements PuestoVacunacionRepositoryLocal {
	@PersistenceContext(name="sist-centralPersistenceUnit")
    private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public PuestoVacunacionRepository() {
        // TODO Auto-generated constructor stub
    }



    public List<PuestoVacunacion> find() {
        return entityManager.createQuery("select e from PuestoVacunacion e", PuestoVacunacion.class)
                .getResultList();
    }

    public void save(PuestoVacunacion puesto) {
        entityManager.persist(puesto);
    }

    public Optional<PuestoVacunacion> find(long id) {
        List<PuestoVacunacion> resultList = entityManager.createQuery(
                "select e from PuestoVacunacion e where e.id = :id",
                PuestoVacunacion.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

}
