package datos.repositorios;
import datos.dtos.EtapaDTO2;
import datos.entidades.*;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Singleton()
public class EtapaRepository implements EtapaRepositoryLocal, EtapaRepositoryRemote {

    @PersistenceContext(unitName = "Vacunatorio2PersistenceUnit")
	private EntityManager entityManager;

    public EtapaRepository() {
    }

    @Override
    public List<Etapa> find() {
        return entityManager.createQuery("select e from Etapa e", Etapa.class)
                .getResultList();
    }

    @Override
    public void save(Etapa etapa) {
        entityManager.persist(etapa);
    }

    @Override
    public Optional<Etapa> find(int id) {
        List<Etapa> resultList = entityManager.createQuery(
                "select e from Etapa e where e.id = :id",
                Etapa.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    @Override
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

    @Override
	public void save(String vac, LocalDate inicio, LocalDate fin, PlanVacunacion planVacunacion, String descripcion,
                     List<Trabajos> trabajos, int edadMin, int edadMax) {
		RestriccionEtapa restricciones = new RestriccionEtapa(trabajos, edadMin, edadMax);
		Etapa res = new Etapa(descripcion, inicio, fin, vac, planVacunacion);
		entityManager.persist(res);
	}

	@Override
    public boolean existeEtapaParaCiudadano(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos) {
        TypedQuery<Long> etapaTypedQuery = entityManager.createQuery(
                "select count(e) from Etapa e left join e.restricciones.filtroEmpleoEn f " +
                        "where e.planVacunacion.enfermedad.nombre = :nombreEnfermedad " +
                        "and current_date between e.inicio and e.fin " +
                        "and (e.restricciones.mayorIgual is null or :edadCiudadano >= e.restricciones.mayorIgual) " +
                        "and (e.restricciones.mayorIgual is null or :edadCiudadano <= e.restricciones.menorIgual) " +
                        "group  by e " +
                        "having count(f) = 0 " +
                        (trabajos != null ? "or :filtroPorEmpleo member of f " : "") +
                        "order by e.id", Long.class)
                .setParameter("nombreEnfermedad", nombreEnfermedad)
                .setParameter("edadCiudadano", edadCiudadano);

        if(trabajos != null) {
            etapaTypedQuery.setParameter("filtroPorEmpleo", trabajos);
        }

        return etapaTypedQuery.getSingleResult() > 0;
    }

	@Override
	public Etapa find(EtapaDTO2 etapaDTO) {
		Etapa res = null;
		List<Etapa> etapas = find();
		for(Etapa etapa:etapas) {
			if(etapa.getDescripcion().equals(etapaDTO.getDescripcion()) && etapa.getVacuna().equals(etapaDTO.getVacuna()) && etapa.getPlanVacunacion().getNombre().equals(etapaDTO.getPlanVac().getNombre()) && etapa.getInicio().toString().equals(etapaDTO.getInicio()) && etapa.getFin().toString().equals(etapaDTO.getFin())) {
				res = etapa;
				break;
			}
		}
		return res;
	}
	
	public void drop() {
		entityManager.createQuery("delete from Etapa").executeUpdate();	
		
	}
}
