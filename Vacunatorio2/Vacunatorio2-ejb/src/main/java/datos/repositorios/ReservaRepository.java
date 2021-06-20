package datos.repositorios;

import datos.entidades.Ciudadano;
import datos.entidades.Estado;
import datos.entidades.Reserva;
import datos.entidades.ReservaConfirmada;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class ReservaRepository implements ReservaRepositoryLocal, ReservaRepositoryRemote{
    @PersistenceContext(unitName = "Vacunatorio2PersistenceUnit")
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
    
    public void drop() {
		entityManager.createQuery("delete from Reserva").executeUpdate();	
		
	}

	@Override
	public List<Reserva> find() {
		return entityManager.createQuery("SELECT r FROM Reserva r", Reserva.class).getResultList();
	}

	@Override
	public ReservaConfirmada findReservaConfirmada(int codigo) {
	    return entityManager.find(ReservaConfirmada.class, codigo);
	}

	@Override
	public void saveReservaConfrimada(ReservaConfirmada resConf) {
		entityManager.persist(resConf);
		
	}
	
	public void dropReservasConfirmadas() {
		entityManager.createQuery("delete from ReservaConfirmada").executeUpdate();	
		
	}

	@Override
	public List<ReservaConfirmada> findReservasConfirmadas() {
		return entityManager.createQuery("SELECT r FROM ReservaConfirmada r", ReservaConfirmada.class).getResultList();
	}
}
