package datos.repositorios;
import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Singleton()
public class EtapaRepository {
    @Inject
    private EntityManager entityManager;

    public EtapaRepository() {
    }

    public List<Etapa> find() {
        return entityManager.createQuery("select e from Etapa e", Etapa.class)
                .getResultList();
    }

    public void save(PlanVacunacion plan) {
        entityManager.persist(plan);
    }

    public Optional<Etapa> find(long id) {
        List<Etapa> resultList = entityManager.createQuery(
                "select e from Etapa e where e.id = :id",
                Etapa.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }
}
