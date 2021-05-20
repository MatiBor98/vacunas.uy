package datos.repositorios;

import datos.entidades.Intervalo;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class IntervaloRepository {
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public List<Intervalo> getIntervalos(LocalDateTime inicio, LocalDateTime fin, int agendaId) {
        return entityManager.createQuery(
                "select i from Intervalo i " +
                        "join fetch i.reservas " +
                        "where i.agenda.id = :agendaId " +
                        "and i.fechayHora between :inicio and :fin ", Intervalo.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .setParameter("agendaId", agendaId)
                .getResultList();
    }
}
