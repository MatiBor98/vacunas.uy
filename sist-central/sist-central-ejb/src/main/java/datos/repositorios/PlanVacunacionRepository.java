package datos.repositorios;
import datos.entidades.PlanVacunacion;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Singleton()
public class PlanVacunacionRepository {
    @Inject
    private EntityManager entityManager;

    public PlanVacunacionRepository() {
    }

    public List<PlanVacunacion> find() {
        return entityManager.createQuery("select p from PlanVacunacion p join fetch p.etapas", PlanVacunacion.class)
                .getResultList();
    }

    public void save(PlanVacunacion plan) {
        entityManager.persist(plan);
    }

    public Optional<PlanVacunacion> find(long id) {
        List<PlanVacunacion> resultList = entityManager.createQuery(
                "select p from PlanVacunacion p join fetch p.etapas where p.id = :id",
                PlanVacunacion.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }
}
