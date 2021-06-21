package datos.repositorios;

import datos.entidades.Turno;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class TurnoRepository implements TurnoRepositoryLocal {


    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public TurnoRepository() {
    }

    @Override
    public List<Turno> find() {
        return entityManager.createQuery(
                "select t from Turno t", Turno.class)
                .getResultList();
    }

    @Override
    public Optional<Turno> findById(int id) {
        List<Turno> resultado = entityManager.createQuery(
                "select t from Turno t where t.id = :id", Turno.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    @Override
    public void save(Turno turno) {
        entityManager.persist(turno);

    }

    public List<Turno> find(String vac, String nombreTurno) {
        Query query = entityManager.createQuery("SELECT t FROM Turno t WHERE lower(t.nombre) = :nombreTurno").setParameter("nombreTurno", nombreTurno.toLowerCase());
        //Query query = entityManager.createQuery("SELECT p FROM PuestoVacunacion p WHERE lower(p.nombrePuesto) like :nombrePuesto and lower(p.vacunatorio) like :vac").setParameter("vac", "%" + vac + "%").setParameter("nombrePuesto", "%" + nombrePuesto.toLowerCase() + "%");
        List<Turno> turns = query.getResultList();
        List<Turno> res = new ArrayList<>();
        for (Turno turn : turns) {
            if ((turn.getVacunatorio().getNombre().equals(vac))) {
                res.add(turn);
            }
        }
        return res;
    }

    @Override
    public long findCount() {
        return entityManager.createQuery(
                "select count(t) from Turno t", Long.class)
                .getSingleResult();
    }

    @Override
    public List<Turno> findPage(int offset, int size) {
        return entityManager.createQuery(
                "select t from Turno t order by t.vacunatorio.id, t.inicio", Turno.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }
}