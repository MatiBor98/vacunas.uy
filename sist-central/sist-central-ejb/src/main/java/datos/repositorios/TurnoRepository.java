package datos.repositorios;

import datos.entidades.Turno;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Singleton
public class TurnoRepository {

    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public TurnoRepository() {
    }

    public List<Turno> find() {
        return entityManager.createQuery(
                "select t from Turno t join fetch t.vacunatorio v", Turno.class)
                .getResultList();
    }

    public Optional<Turno> findById(long id) {
        List<Turno> resultado = entityManager.createQuery(
                "select t from Turno t join fetch t.vacunatorio v where t.id = :id", Turno.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }
}