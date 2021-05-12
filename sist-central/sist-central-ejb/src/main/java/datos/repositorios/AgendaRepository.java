package datos.repositorios;

import datos.entidades.Agenda;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Singleton
public class AgendaRepository implements AgendaRepositoryLocal {
<<<<<<< HEAD
	@PersistenceContext(name="sist-centralPersistenceUnit")
=======
	
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
>>>>>>> branch 'nico_vacunatorios' of https://gitlab.fing.edu.uy/nicolas.san.martin/laboratorio-tse-2021.git
    private EntityManager entityManager;

    public AgendaRepository() {
    }

    @Override
    public List<Agenda> find() {
        return entityManager.createQuery(
                "select a from Agenda a " +
                    "join fetch a.etapa e " +
                    "join fetch e.planVacunacion p", Agenda.class)
                .getResultList();
    }
    @Override
    public List<Agenda> findByNombrePlan(String criterio) {
        return entityManager.createQuery(
                    "select a from Agenda a " +
                        "join fetch a.etapa e " +
                        "join fetch e.planVacunacion p " +
                        "where lower(p.nombre) like :criterio", Agenda.class)
                .setParameter("criterio", "%" + criterio.toLowerCase() + "%")
                .getResultList();
    }

    @Override
    public void save(Agenda agenda) {
        entityManager.persist(agenda);
    }
}