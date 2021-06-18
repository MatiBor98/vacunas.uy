package datos.repositorios;

import datos.entidades.Estado;
import datos.entidades.Reserva;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
@LocalBean
public class ReservaRepository {
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public void save(Reserva reserva) {
        entityManager.persist(reserva);
    }

    public boolean existeReservaPendienteByCiudadanoEnfermedad(int ci, String enfermedad) {
        return entityManager.createQuery(
                "select count(r) from Reserva r " +
                        "where r.ciudadano.ci = :ci " +
                        "and r.estado = :pendiente " +
                        "and r.intervalo.agenda.etapa.planVacunacion.enfermedad.nombre = :enfermedad", Long.class)
                .setParameter("ci", ci)
                .setParameter("pendiente", Estado.PENDIENTE)
                .setParameter("enfermedad", enfermedad)
                .getSingleResult() > 0;
    }

    public List<Reserva> listar(int offset, int limit, int ci) {
        return entityManager.createQuery(
                "select r from Reserva r " +
                        "where r.ciudadano.ci = :ci " +
                        "order by r.id desc", Reserva.class)
                .setParameter("ci", ci)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long listarCount(int ci) {
        return entityManager.createQuery(
                "select count(r) from Reserva r " +
                        "where r.ciudadano.ci = :ci ", Long.class)
                .setParameter("ci", ci)
                .getSingleResult();
    }

    public Reserva getByCiAndCodigo(int ci, int codigo) {
        return entityManager.createQuery(
                "select r from Reserva r " +
                        "where r.ciudadano.ci = :ci " +
                        "and r.codigo = :codigo", Reserva.class)
                .setParameter("ci", ci)
                .setParameter("codigo", codigo)
                .getSingleResult();
    }

    public List<Reserva> getPendientesByCiAndCodigoAgenda(int ci, int idAngeda) {
        return entityManager.createQuery(
                "select r from Reserva r " +
                        "where r.ciudadano.ci = :ci " +
                        "and r.intervalo.agenda.id = :idAngeda " +
                        "and r.estado = :pendiente", Reserva.class)
                .setParameter("ci", ci)
                .setParameter("idAngeda", idAngeda)
                .setParameter("pendiente", Estado.PENDIENTE)
                .getResultList();
    }
}