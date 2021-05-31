package datos.repositorios;

import datos.entidades.Estado;
import datos.entidades.Reserva;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
