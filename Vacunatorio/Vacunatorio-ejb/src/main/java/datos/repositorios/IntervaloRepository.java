package datos.repositorios;

import datos.dtos.TurnoDTO;
import datos.entidades.Ciudadano;
import datos.entidades.Etapa;
import datos.entidades.Intervalo;
import datos.entidades.Reserva;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Singleton
public class IntervaloRepository implements IntervaloRepositoryLocal, IntervaloRepositoryRemote{
    @PersistenceContext(unitName = "vacunatorioPersistenceUnit")
    private EntityManager entityManager;

    public List<Intervalo> getIntervalos(LocalDateTime inicio, LocalDateTime fin, int agendaId) {
        return entityManager.createQuery(
                "select distinct i from Intervalo i " +
                        "join fetch i.reservas " +
                        "where i.agenda.id = :agendaId " +
                        "and i.fechayHora between :inicio and :fin ", Intervalo.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .setParameter("agendaId", agendaId)
                .getResultList();
    }

    public Optional<Intervalo> find(int agendaId, LocalDateTime inicio) {
        return entityManager.createQuery(
                "select distinct i from Intervalo i " +
                        "join fetch i.reservas " +
                        "where i.agenda.id = :agendaId " +
                        "and i.fechayHora = :inicio ", Intervalo.class)
                .setParameter("inicio", inicio)
                .setParameter("agendaId", agendaId)
                .getResultList()
                .stream()
                .findFirst();
    }
    
    public List<Intervalo> findByFecha(int agendaId, LocalDateTime inicio) {
        return entityManager.createQuery(
                "select distinct i from Intervalo i " +
                        "where i.agenda.id = :agendaId " +
                        "and i.fechayHora = :inicio ", Intervalo.class)
                .setParameter("inicio", inicio)
                .setParameter("agendaId", agendaId)
                .getResultList();
    }
    
    public Intervalo findOrCreate(Intervalo intervalo) {
        return find(intervalo.getAgenda().getId(), intervalo.getFechayHora()).orElseGet(() -> {
            entityManager.persist(intervalo);
            return intervalo;
        });
    }

	public List<Intervalo> find() {
		return entityManager.createQuery("select i from Intervalo i", Intervalo.class)
                .getResultList();
	}
	
	public void save(Intervalo intervalo) {
		entityManager.persist(intervalo);
	}
	
	public void drop() {
		entityManager.createQuery("delete from Intervalo").executeUpdate();	
		
	}

}
