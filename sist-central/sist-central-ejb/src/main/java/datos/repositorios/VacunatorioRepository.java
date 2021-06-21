package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import datos.exceptions.VacunatorioNoExistenteException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.util.GeometricShapeFactory;

import datos.entidades.Vacunatorio;
import datos.entidades.Departamento;
import datos.entidades.Lote;

/**
 * Session Bean implementation class VacunatorioRepository
 */
@Singleton
public class VacunatorioRepository implements VacunatorioRepositoryLocal {

    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public VacunatorioRepository() {
    }

    public List<Vacunatorio> find() {
        return entityManager.createQuery("SELECT e FROM Vacunatorio e", Vacunatorio.class)
                .getResultList();
    }

    public void save(Vacunatorio vac) {
        entityManager.persist(vac);
    }

	@Override
	public void addLoteAVacuantorio(Lote lote, String nombreVacunatorio) {
			Vacunatorio vacunatorio = find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
			vacunatorio.getLotes().add(lote);
	}

	public Optional<Vacunatorio> find(String nombre) {
        List<Vacunatorio> resultList = entityManager.createQuery(
                "select e from Vacunatorio e where e.nombre = :nombre",
                Vacunatorio.class)
                .setParameter("nombre", nombre)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }
    
    @Transactional
    public Optional<Vacunatorio> findWithEverything(String nombre) {
    	try {
    		Vacunatorio vac = entityManager.find(Vacunatorio.class, nombre);
            vac.getPuestosVacunacion().size();
            vac.getTurnos().size();
            return Optional.of(vac);
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
        
    }

	@Override
	public List<Vacunatorio> find(int primerResultado, int maximosResultados) {
		return entityManager.createQuery("select e from Vacunatorio e order by e.nombre", Vacunatorio.class)
                .setFirstResult(primerResultado)
                .setMaxResults(maximosResultados)
                .getResultList();
	}
	
	
	public List<Vacunatorio> findByDepartamento(Departamento dep) {
		return entityManager.createQuery(
				"select distinct v from Vacunatorio v"
						+ " left join fetch v.lotes lot"
						+ " left join fetch lot.vacuna vacuna"
						+ " left join fetch vacuna.enfermedades"
						+ " where v.departamento = :departamento"
						, Vacunatorio.class)
				.setParameter("departamento", dep)
				.getResultList();
	}

	public List<Vacunatorio> findByDepartamento(Departamento dep, int primerResultado, int maximosResultados ) {
		return entityManager.createQuery(
				"select distinct v from Vacunatorio v"
						+ " left join fetch v.lotes lot"
						+ " left join fetch lot.vacuna vacuna"
						+ " left join fetch vacuna.enfermedades"
						+ " where v.departamento = :departamento"
						, Vacunatorio.class)
				.setParameter("departamento", dep)
				.setFirstResult(primerResultado)
                .setMaxResults(maximosResultados)
				.getResultList();
	}

	@Override
	public long getDosisDisponiblesVacunaCount(String vacunatorio, String vacuna) {
		return entityManager.createQuery(
				"select sum(lot.dosisDisponibles) from Vacunatorio vio"
						+ " inner join vio.lotes lot"
						+ " inner join lot.vacuna vna"
						+ " where vio.id = :vacunaotrio "
						+ " and vna.id = :vacuna "
						+ " and lot.fechaVencimiento > current_date ", Long.class)
				.setParameter("vacunaotrio", vacunatorio)
				.setParameter("vacuna", vacuna)
				.getSingleResult();
	}

	@Override
	public long getReservasPendientesVacunaCount(String vacunatorio, String vacuna) {
		return entityManager.createQuery(
				"select count(distinct r.id) from Vacunatorio vio"
						+ " inner join Turno t on t.vacunatorio=vio "
						+ " inner join Agenda a on a.turno=t "
						+ " inner join Intervalo i on i.agenda = a "
						+ " inner join a.etapa e "
						+ " inner join e.vacuna vna "
						+ " inner join i.reservas r "
						+ " where vio.id = :vacunaotrio "
						+ " and vna.id = :vacuna "
						+ " and r.estado = datos.entidades.Estado.PENDIENTE ", Long.class)
				.setParameter("vacunaotrio", vacunatorio)
				.setParameter("vacuna", vacuna)
				.getSingleResult();
	}
	public List<Vacunatorio> findVacunatorioCercano(Double coordX, Double coordY) {
    	
		/*return entityManager.createNativeQuery(
				"SELECT * FROM vacunatorio WHERE ST_DWithin( ST_SetSRID(ubicacion, 4326)::geography , ST_GeomFromText('POINT("+ coordX.doubleValue() +" "+ coordY.doubleValue()+  ")', 4326)::geography, 50000)", Vacunatorio.class)
				.getResultList();*/
		
		return entityManager.createNativeQuery(
				"select * " +
				"from vacunatorio " +
				"WHERE ST_DWithin(ST_SetSRID(vacunatorio.ubicacion, 4326), " +
				                 "ST_GeomFromText('POINT("+ coordX +" "+ coordY +")', 4326), " +
				                 "0.4)", Vacunatorio.class)
				.getResultList();
		
	}
	
}

