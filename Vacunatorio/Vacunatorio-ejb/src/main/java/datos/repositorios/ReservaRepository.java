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
public class ReservaRepository implements ReservaRepositoryLocal, ReservaRepositoryRemote {
	@PersistenceContext(unitName = "vacunatorioPersistenceUnit")
	private EntityManager entityManager;

	public void save(Reserva reserva) {
		entityManager.persist(reserva);
	}

	public boolean existeReservaPendienteByCiudadanoEnfermedad(int ci, String enfermedad) {
		return entityManager
				.createQuery(
						"select count(r) from Reserva r " + "where r.ciudadano.ci = :ci " + "and r.estado = :pendiente "
								+ "and r.intervalo.agenda.etapa.planVacunacion.enfermedad.nombre = :enfermedad",
						Long.class)
				.setParameter("ci", ci).setParameter("pendiente", Estado.PENDIENTE)
				.setParameter("enfermedad", enfermedad).getSingleResult() > 0;

	}

	public void drop() {
		entityManager.createQuery("delete from Reserva").executeUpdate();

	}

	public List<Reserva> listarPendientes(int offset, int limit, String busqueda) {
		if (busqueda == null || busqueda.equals("")) {
			return entityManager
					.createQuery("select r from Reserva r " + "where r.estado = :pendiente", Reserva.class)
					.setParameter("pendiente", Estado.PENDIENTE)
					.setFirstResult(offset).setMaxResults(limit).getResultList();
			
		} else {
			return entityManager
					.createQuery("select r from Reserva r " + "where r.estado = :pendiente and lower(r.ciudadano.nombre) like :busqueda", Reserva.class)
					.setParameter("pendiente", Estado.PENDIENTE)
					.setParameter("busqueda", "%"+busqueda.toLowerCase().trim()+"%")
					.setFirstResult(offset).setMaxResults(limit).getResultList();
		}
		
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

	@Override
	public List<Reserva> listarVacunados(int offset, int limit, String busqueda) {
		if (busqueda == null || busqueda.equals("")) {
			return entityManager
				.createQuery("select r from Reserva r " + "where r.estado = :vacunado", Reserva.class)
				.setParameter("vacunado", Estado.VACUNADO)
				.setFirstResult(offset).setMaxResults(limit).getResultList();
		} else {
			return entityManager
					.createQuery("select r from Reserva r " + "where r.estado = :cancelado and lower(r.ciudadano.nombre) like :busqueda", Reserva.class)
					.setParameter("cancelado", Estado.VACUNADO)
					.setParameter("busqueda", "%"+busqueda.toLowerCase().trim()+"%")
					.setFirstResult(offset).setMaxResults(limit).getResultList();
		}

	}
	
	public List<Reserva> listarCancelados(int offset, int limit, String busqueda) {
		if (busqueda == null || busqueda.equals("")) {
			return entityManager
				.createQuery("select r from Reserva r " + "where r.estado = :cancelado", Reserva.class)
				.setParameter("cancelado", Estado.CANCELADA)
				.setFirstResult(offset).setMaxResults(limit).getResultList();
		} else {
			return entityManager
					.createQuery("select r from Reserva r " + "where r.estado = :cancelado and lower(r.ciudadano.nombre) like :busqueda", Reserva.class)
					.setParameter("cancelado", Estado.CANCELADA)
					.setParameter("busqueda", "%"+busqueda.toLowerCase().trim()+"%")
					.setFirstResult(offset).setMaxResults(limit).getResultList();
		}

	}

	@Override
	public List<Reserva> findByEstado(Estado estado) {
		return entityManager
				.createQuery("select r from Reserva r " + "where r.estado = :estado", Reserva.class)
				.setParameter("estado", estado)
				.getResultList();
	}

	@Override
	public List<ReservaConfirmada> listarConfirmados(int offset, int limit, String busqueda) {
		if (busqueda == null || busqueda.equals("")) {
			return entityManager
					.createQuery("select r from ReservaConfirmada r " + "where r.estado = :estado", ReservaConfirmada.class)
					.setParameter("estado", "pendiente")
					.setFirstResult(offset).setMaxResults(limit).getResultList();
		} else {
			return entityManager
					.createQuery("select r from ReservaConfirmada r " + "where r.estado = :estado and lower(r.nombre) like :busqueda", ReservaConfirmada.class)
					.setParameter("estado", "pendiente")
					.setParameter("busqueda", "%"+busqueda.toLowerCase().trim()+"%")
					.setFirstResult(offset).setMaxResults(limit).getResultList();
		}

	}

	@Override
	public List<ReservaConfirmada> findConfrimados() {
		return entityManager
				.createQuery("select r from ReservaConfirmada r " + "where r.estado = :estado", ReservaConfirmada.class)
				.setParameter("estado", "pendiente")
				.getResultList();
	}

	@Override
	public Reserva findReserva(int codigo) {
		return entityManager.find(Reserva.class, codigo);
	}
}
