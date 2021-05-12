package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import datos.entidades.Vacunatorio;
import datos.entidades.PlanVacunacion;

/**
 * Session Bean implementation class VacunatorioRepository
 */
@Singleton
@LocalBean
public class VacunatorioRepository implements VacunatorioRepositoryLocal {

    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public VacunatorioRepository() {
    }

    public List<Vacunatorio> find() {
        return entityManager.createQuery("select e from Vacunatorio e", Vacunatorio.class)
                .getResultList();
    }

    public void save(Vacunatorio vac) {
        entityManager.persist(vac);
    }

    public Optional<Vacunatorio> find(String nombre) {
        List<Vacunatorio> resultList = entityManager.createQuery(
                "select e from Vacunatorio e where e.nombre = :nombre",
                Vacunatorio.class)
                .setParameter("nombre", nombre)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }
}

