package datos.repositorios;
import datos.entidades.Ciudadano;
import datos.entidades.PlanVacunacion;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Singleton()
public class PlanVacunacionRepository implements PlanVacunacionRepositoryLocal, PlanVacunacionRepositoryRemote{


    @PersistenceContext(unitName = "vacunatorioPersistenceUnit")
    private EntityManager entityManager;

    public PlanVacunacionRepository() {
    }

    public List<PlanVacunacion> find() {
        return entityManager.createQuery("select p from PlanVacunacion p", PlanVacunacion.class)
                .getResultList();
    }

    public void save(PlanVacunacion plan) {
        entityManager.persist(plan);
    }

    public Optional<PlanVacunacion> find(String nombre) {
        List<PlanVacunacion> resultList = entityManager.createQuery(
                "select p from PlanVacunacion p where p.nombre = :nombre", PlanVacunacion.class)
                .setParameter("nombre", nombre)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

	public void save(String nombre, LocalDate inicio, LocalDate fin, String enfermedad) {
		PlanVacunacion planVac = new PlanVacunacion(nombre, inicio, fin, enfermedad);
		entityManager.persist(planVac);
	}
	
	public void drop() {
		entityManager.createQuery("delete from PlanVacunacion").executeUpdate();	
		
	}
	
	public PlanVacunacion findByNombre(String criterio) {
    	return entityManager.find(PlanVacunacion.class, criterio);
    }


}
