package datos.repositorios;
import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Singleton()
public class EtapaRepository {
<<<<<<< HEAD
	@PersistenceContext(name="sist-centralPersistenceUnit")
    private EntityManager entityManager;
=======

    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
	private EntityManager entityManager;
>>>>>>> branch 'nico_vacunatorios' of https://gitlab.fing.edu.uy/nicolas.san.martin/laboratorio-tse-2021.git

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
