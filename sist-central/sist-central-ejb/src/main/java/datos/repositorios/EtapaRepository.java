package datos.repositorios;
import datos.entidades.Departamento;
import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;
import datos.entidades.RestriccionEtapa;
import datos.entidades.Trabajos;
import datos.entidades.Vacuna;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Singleton()
public class EtapaRepository implements EtapaRepositoryLocal{

    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
	private EntityManager entityManager;

    public EtapaRepository(){
    }

    public List<Etapa> find() {
        return entityManager.createQuery("select e from Etapa e", Etapa.class)
                .getResultList();
    }

    public void save(Etapa etapa) {
        entityManager.persist(etapa);
    }

    public Optional<Etapa> find(int id) {
        List<Etapa> resultList = entityManager.createQuery(
                "select e from Etapa e where e.id = :id",
                Etapa.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    public List<Etapa> find(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos) {
        TypedQuery<Etapa> etapaTypedQuery = entityManager.createQuery(
                "select e from Etapa e left join e.restricciones.filtroEmpleoEn f " +
                    "where e.planVacunacion.enfermedad.nombre = :nombreEnfermedad " +
                    "and current_date between e.inicio and e.fin " +
                    "and (e.restricciones.mayorIgual is null or :edadCiudadano >= e.restricciones.mayorIgual) " +
                    "and (e.restricciones.mayorIgual is null or :edadCiudadano <= e.restricciones.menorIgual) " +
                    "group  by e " +
                    "having count(f) = 0 " +
                        (trabajos != null ? "or :filtroPorEmpleo member of f " : "") +
                    "order by e.id", Etapa.class)
                .setParameter("nombreEnfermedad", nombreEnfermedad)
                .setParameter("edadCiudadano", edadCiudadano);

        if(trabajos != null) {
            etapaTypedQuery.setParameter("filtroPorEmpleo", trabajos);
        }

        return etapaTypedQuery.getResultList();
    }

	public void save(Vacuna vac, LocalDate inicio, LocalDate fin, PlanVacunacion planVacunacion, String descripcion, List<Trabajos> trabajos, int edadMin, int edadMax) {
		RestriccionEtapa restricciones = new RestriccionEtapa(trabajos, edadMin, edadMax);
		Etapa res = new Etapa(restricciones, descripcion, inicio, fin, vac, planVacunacion);
		entityManager.persist(res);
	}
}
