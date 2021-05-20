package datos.repositorios;

import datos.entidades.Agenda;
import datos.entidades.Departamento;
import datos.entidades.Intervalo;
import datos.entidades.Trabajos;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Singleton
public class AgendaRepository implements AgendaRepositoryLocal {
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public AgendaRepository() {
    }

    @Override
    public List<Agenda> find() {
        return entityManager.createQuery(
                "select a from Agenda a " +
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
                    "select a from Agenda a " +
                        "join fetch a.etapa e " +
                        "join fetch e.planVacunacion p " +
                        "where lower(p.nombre) like :criterio", Agenda.class)
                .setParameter("criterio", "%" + criterio.toLowerCase() + "%")
                .getResultList();
    }

    @Override
    public List<Agenda> findAgendasParaCiudadanoPorDepartamento(String nombreEnfermedad, int edadCiudadano,
                                                                Trabajos trabajos, Departamento departamento) {
        TypedQuery<Agenda> etapaTypedQuery = entityManager.createQuery(
                "select a " +
                        "from Agenda a " +
                        "inner join a.etapa e " +
                        "left join e.restricciones.filtroEmpleoEn f " +
                        "where e.planVacunacion.enfermedad.nombre = :nombreEnfermedad " +
                        "and current_date between a.inicio and a.fin " +
                        "and (e.restricciones.mayorIgual is null or :edadCiudadano >= e.restricciones.mayorIgual) " +
                        "and (e.restricciones.mayorIgual is null or :edadCiudadano <= e.restricciones.menorIgual) " +
                        "and a.cantidadCuposDisponbiles > 0 " +
                        "and a.turno.vacunatorio.departamento = :departamento " +
                        "group by a " +
                        "having count(f) = 0 " +
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