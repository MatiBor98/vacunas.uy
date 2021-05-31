package datos.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import datos.entidades.Asignacion;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Vacuna;
import datos.entidades.Vacunatorio;

/**
 * Session Bean implementation class PuestoVacunacionRepository
 */
@Singleton
public class PuestoVacunacionRepository implements PuestoVacunacionRepositoryLocal {
	
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
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

    public Optional<PuestoVacunacion> find(int id) {
        List<PuestoVacunacion> resultList = entityManager.createQuery(
                "select e from PuestoVacunacion e where e.id = :id",
                PuestoVacunacion.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }
    
    public List<PuestoVacunacion> find(String vac, String nombrePuesto) {
    	Query query = entityManager.createQuery("SELECT p FROM PuestoVacunacion p WHERE lower(p.nombrePuesto) like :nombrePuesto").setParameter("nombrePuesto", "%" + nombrePuesto.toLowerCase() + "%");
		//Query query = entityManager.createQuery("SELECT p FROM PuestoVacunacion p WHERE lower(p.nombrePuesto) like :nombrePuesto and lower(p.vacunatorio) like :vac").setParameter("vac", "%" + vac + "%").setParameter("nombrePuesto", "%" + nombrePuesto.toLowerCase() + "%");
    	List<PuestoVacunacion> pVacs = query.getResultList();
    	List<PuestoVacunacion> res = new ArrayList<>();
    	for(PuestoVacunacion pVac:pVacs) {
    		if ((pVac.getVacunatorio().getNombre().equals(vac))) {
    			res.add(pVac);
    		}
    	}
		return res;
    }



	@Override
	public List<Asignacion> getAsignaciones(String nombreVacunatorio, String nombrePuesto) {
		PuestoVacunacion pVac = find(nombreVacunatorio, nombrePuesto).get(0);
		return pVac.getAsignaciones();
	}



	@Override
	public void addAsignacion(Asignacion asig) {
		entityManager.persist(asig);
	}

}
