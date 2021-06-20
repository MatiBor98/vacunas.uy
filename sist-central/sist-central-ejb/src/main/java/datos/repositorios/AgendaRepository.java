package datos.repositorios;

import datos.entidades.Agenda;
import datos.entidades.Departamento;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.AccessTimeout;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Stateless
public class AgendaRepository implements AgendaRepositoryLocal {
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public AgendaRepository() {
    }

    @Override
    public List<Agenda> find() {
        return entityManager.createQuery(
                "select distinct a from Agenda a " +
                    "join fetch a.etapa e " +
                    "join fetch e.planVacunacion p", Agenda.class)
                .getResultList();
    }

    @Override
    public Optional<Agenda> find(int id) {
        List<Agenda> resultado = entityManager.createQuery(
                "select a from Agenda a where a.id = :id", Agenda.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    @Override
    public List<Agenda> findByNombrePlan(String criterio) {
        return entityManager.createQuery(
                    "select distinct a from Agenda a " +
                        "join fetch a.etapa e " +
                        "join fetch e.planVacunacion p " +
                        "where lower(p.nombre) like :criterio", Agenda.class)
                .setParameter("criterio", "%" + criterio.toLowerCase() + "%")
                .getResultList();
    }

    @Override
    public List<Agenda> findAgendasParaCiudadanoPorDepartamento(String nombreEnfermedad, int edadCiudadano,
                                                                Trabajo trabajos, Departamento departamento) {
        TypedQuery<Agenda> etapaTypedQuery = entityManager.createQuery(
                "select distinct a " +
                        "from Agenda a " +
                        "join fetch a.etapa e " +
                        "join fetch a.turno t " +
                        "join fetch t.vacunatorio v " +
                        "join fetch e.vacuna vac " +
                        "inner join e.planVacunacion p " +
                        "inner join p.enfermedad enf " +
                        "left join e.restricciones.filtroEmpleoEn f " +
                        "left join Intervalo i on i.agenda = a " +
                        "left join i.reservas r on r.estado = datos.entidades.Estado.PENDIENTE " +
                        "where enf.nombre = :nombreEnfermedad " +
                        "and current_date >= a.inicio " +
                        "and (e.restricciones.mayorIgual is null or :edadCiudadano >= e.restricciones.mayorIgual) " +
                        "and (e.restricciones.mayorIgual is null or :edadCiudadano <= e.restricciones.menorIgual) " +
                        "and a.fin is null " +
                        "and v.departamento = :departamento " +
                        "group by a, e, t, v, vac " +
                        "having (" +
                        "   select sum(l.dosisDisponibles) " +
                        "   from v.lotes l where l.vacuna = vac and l.fechaVencimiento > current_date) " +
                        "           >= count(r) + vac.cantDosis " +
                        "and count(f) = 0 " +
                        (trabajos != null ? "or :filtroPorEmpleo member of f " : ""), Agenda.class)
                .setParameter("nombreEnfermedad", nombreEnfermedad)
                .setParameter("edadCiudadano", edadCiudadano)
                .setParameter("departamento", departamento);

        if(trabajos != null) {
            etapaTypedQuery.setParameter("filtroPorEmpleo", trabajos);
        }

        return etapaTypedQuery.getResultList();
    }

    @Override
    public void save(Agenda agenda) {
        entityManager.persist(agenda);
    }
}