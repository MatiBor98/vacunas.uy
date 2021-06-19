package datos.repositorios;

import datos.entidades.Ciudadano;
import datos.dtos.ReservaDTO;
import datos.entidades.Ciudadano;
import datos.entidades.Departamento;
import datos.entidades.Estado;
import datos.entidades.Lote;
import datos.entidades.Reserva;
import datos.entidades.Vacunatorio;
import plataformainteroperabilidad.Sexo;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@LocalBean
public class ReservaRepository implements ReservaRepositoryLocal{
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

	@Override
	public List<Reserva> findReservasVacunatorio(Vacunatorio vac) {
		List<Reserva> reservas = find();
		List<Reserva> res = new ArrayList<>();
		for(Reserva reserva:reservas) {
			if(reserva.getIntervalo().getAgenda().getTurno().getVacunatorio().getNombre().equals(vac.getNombre())) {
				res.add(reserva);
			}
		}
		return res;
	}

	@Override
	public List<Reserva> find() {
		return entityManager.createQuery(
                "select r from Reserva r", Reserva.class)
                .getResultList();
	}

	@Override
	public Reserva findByID(int codigo) {
		return entityManager.find(Reserva.class, codigo);

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

    public List<Reserva> findCantidadDosisDadasDepartamento(Departamento departamento, String enfermedad, String vacuna, int etapa,
                                                            LocalDate inicio, LocalDate fin){

        String qlString = " select r from Reserva r " +
                "join Intervalo i on (r.intervalo.id = i.id) "
                + "join Agenda a on (i.agenda.id = a.id) "
                + "join Etapa e on (a.etapa.id = e.id) "
                + "join Vacuna v on (e.vacuna.nombre = v.nombre) "
                + "join v.enfermedades enf "
                + "where r.estado = :vacunado "
                + "and a.turno.vacunatorio.departamento = :departamento " +
                " and i.fechayHora >= :inicio and i.fechayHora <= :fin";
        if (!enfermedad.equals(""))
            qlString += " and enf.nombre = :enfermedad ";
        if (!vacuna.equals(""))
            qlString += " and v.nombre = :vacuna ";
        if (etapa != -1)
            qlString += " and e.id = :id ";

        Query query = entityManager.createQuery(
                qlString)
                .setParameter("departamento", departamento)
                .setParameter("vacunado", Estado.VACUNADO)
                .setParameter("inicio", inicio.atStartOfDay())
                .setParameter("fin", fin.atTime(23,59));

        if (!enfermedad.equals(""))
            query.setParameter("enfermedad", enfermedad);
        if (!vacuna.equals(""))
            query.setParameter("vacuna", vacuna);
        if (etapa != -1)
            query.setParameter("etapa", etapa);

        return query.getResultList();
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

    public Integer findVacunadosHoy(String enfermedad, String vacuna, int etapa){

        String qlString = " select count(r) from Reserva r " +
                "join Intervalo i on (r.intervalo.id = i.id) "
                + "join Agenda a on (i.agenda.id = a.id) "
                + "join Etapa e on (a.etapa.id = e.id) "
                + "join Vacuna v on (e.vacuna.nombre = v.nombre) "
                + "join v.enfermedades enf "
                + "where r.estado = :vacunado " +
                " and i.fechayHora >= :hoytemprano and i.fechayHora <= :hoytarde";
        if (!enfermedad.equals(""))
            qlString += " and enf.nombre = :enfermedad ";
        if (!vacuna.equals(""))
            qlString += " and v.nombre = :vacuna ";
        if (etapa != -1)
            qlString += " and e.id = :id ";

        Query query = entityManager.createQuery(
                qlString)
                .setParameter("vacunado", Estado.VACUNADO)
                .setParameter("hoytemprano",LocalDateTime.now().withHour(0).withMinute(0))
                .setParameter("hoytarde",LocalDateTime.now().withHour(23).withMinute(59));

        if (!enfermedad.equals(""))
            query.setParameter("enfermedad", enfermedad);
        if (!vacuna.equals(""))
            query.setParameter("vacuna", vacuna);
        if (etapa != -1)
            query.setParameter("etapa", etapa);

        return ((Long) query.getSingleResult()).intValue();
    }

    public Integer findAgendadosProximos(String enfermedad, String vacuna, int etapa){

        String qlString = " select count(r) from Reserva r " +
                "join Intervalo i on (r.intervalo.id = i.id) "
                + "join Agenda a on (i.agenda.id = a.id) "
                + "join Etapa e on (a.etapa.id = e.id) "
                + "join Vacuna v on (e.vacuna.nombre = v.nombre) "
                + "join v.enfermedades enf "
                + "where r.estado = 0 ";
        if (!enfermedad.equals(""))
            qlString += " and enf.nombre = :enfermedad ";
        if (!vacuna.equals(""))
            qlString += " and v.nombre = :vacuna ";
        if (etapa != -1)
            qlString += " and e.id = :id ";

        Query query = entityManager.createQuery(
                qlString)
                //.setParameter("vacunado", Estado.PENDIENTE)
                ;

        if (!enfermedad.equals(""))
            query.setParameter("enfermedad", enfermedad);
        if (!vacuna.equals(""))
            query.setParameter("vacuna", vacuna);
        if (etapa != -1)
            query.setParameter("etapa", etapa);

        return ((Long) query.getSingleResult()).intValue();
    }



    public List<Reserva> findAllDosisDadas(String enfermedad, String vacuna, int etapa){
        return findAllDosisDadas(enfermedad,vacuna,etapa, LocalDate.of(1999, 1,1),
                LocalDate.of(2800,1,1));
    }


        public List<Reserva> findAllDosisDadas(String enfermedad, String vacuna, int etapa, LocalDate comienzo, LocalDate fin){

        if (comienzo == null || fin == null){
            return findAllDosisDadas(enfermedad,vacuna,etapa);
        }

        String qlString = " select r from Reserva r " +
                "join Intervalo i on (r.intervalo.id = i.id) "
                + "join Agenda a on (i.agenda.id = a.id) "
                + "join Etapa e on (a.etapa.id = e.id) "
                + "join Vacuna v on (e.vacuna.nombre = v.nombre) "
                + "join v.enfermedades enf" +
                " join r.ciudadano "
                + "where r.estado = :vacunado "
                + "and i.fechayHora >= :comienzo "
                + "and i.fechayHora <= :fin ";
        if (!enfermedad.equals(""))
            qlString += " and enf.nombre = :enfermedad ";
        if (!vacuna.equals(""))
            qlString += " and v.nombre = :vacuna ";
        if (etapa != -1)
            qlString += " and e.id = :id ";

        Query query = entityManager.createQuery(
                qlString)
                .setParameter("comienzo", comienzo.atStartOfDay())
                .setParameter("fin", fin.atTime(23,59))
                .setParameter("vacunado", Estado.VACUNADO);

        if (!enfermedad.equals(""))
            query.setParameter("enfermedad", enfermedad);
        if (!vacuna.equals(""))
            query.setParameter("vacuna", vacuna);
        if (etapa != -1)
            query.setParameter("etapa", etapa);


            List<Reserva> resultList = query.getResultList();
            resultList.stream().map(Reserva::getCiudadano).map(Ciudadano::getSexo).collect(Collectors.toList());
            return resultList;
    }

}
