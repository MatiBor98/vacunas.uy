package datos.repositorios;

import datos.entidades.Estado;
import datos.entidades.Reserva;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Reserva> findReservasTomorrow(){
        LocalDateTime hoy = LocalDateTime.now();
        LocalDateTime maniana = hoy.plusDays(1).withHour(23).withMinute(59);
        return entityManager.createQuery(
                " select r from Reserva r join Ciudadano c on (r.ciudadano.ci = c.ci) " +
                        "join Intervalo i on (r.intervalo.id = i.id)"
                        + " where r.intervalo.fechayHora >= :hoy and r.intervalo.fechayHora <= :maniana "
                        + " and r.ciudadano.firebaseTokenMovil is not null"
                )
                .setParameter("hoy",hoy)
                .setParameter("maniana",maniana)
                .getResultList();
    }
}
