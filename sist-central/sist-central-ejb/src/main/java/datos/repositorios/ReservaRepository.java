package datos.repositorios;

import datos.entidades.Departamento;
import datos.entidades.Estado;
import datos.entidades.Reserva;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public List<Reserva> listar(int offset, int limit, int ci) {
        return entityManager.createQuery(
                "select r from Reserva r " +
                        "where r.ciudadano.ci = :ci " +
                        "order by r.id", Reserva.class)
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

    public List<Reserva> findReservasTomorrow() {
        LocalDateTime hoy = LocalDateTime.now();
        LocalDateTime maniana = hoy.plusDays(1).withHour(23).withMinute(59);
        return entityManager.createQuery(
                " select r from Reserva r join Ciudadano c on (r.ciudadano.ci = c.ci) " +
                        "join Intervalo i on (r.intervalo.id = i.id)"
                        + " where r.intervalo.fechayHora >= :hoy and r.intervalo.fechayHora <= :maniana "
                        + " and r.ciudadano.firebaseTokenMovil is not null"
                        + " and r.estado = :pendiente"
        )
                .setParameter("hoy", hoy)
                .setParameter("maniana", maniana)
                .setParameter("pendiente", Estado.PENDIENTE)
                .getResultList();
    }


    public List<Reserva> findDosisDadasTotales(){
        return entityManager.createQuery(
                " select r from Reserva r " +
                        "join Intervalo i on (r.intervalo.id = i.id) "
                        + "join Agenda a on (i.agenda.id = a.id) "
                        + "join Etapa e on (a.etapa.id = e.id) "
                        + "join Vacuna v on (e.vacuna.nombre = v.nombre) "
                        + "join v.enfermedades "
                        + "where r.estado = :vacunado"

        )
                .setParameter("vacunado", Estado.VACUNADO)
                .getResultList();
    }

    public List<Reserva> findCantidadDosisDadasDepartamento(Departamento departamento){
        return entityManager.createQuery(
                " select r from Reserva r " +
                        "join Intervalo i on (r.intervalo.id = i.id) "
                        + "join Agenda a on (i.agenda.id = a.id) "
                        + "join Etapa e on (a.etapa.id = e.id) "
                        + "join Vacuna v on (e.vacuna.nombre = v.nombre) "
                        + "join v.enfermedades "
                        + "where r.estado = :vacunado "
                        + "and a.turno.vacunatorio.departamento = :departamento"

        )
                .setParameter("departamento", departamento)
                .setParameter("vacunado", Estado.VACUNADO)
                .getResultList();
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
                        "and r.intervalo.agenda.id = :idAngeda", Reserva.class)
                .setParameter("ci", ci)
                .setParameter("idAngeda", idAngeda)
                .getResultList();
    }
}
