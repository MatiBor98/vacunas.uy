package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import datos.exceptions.VacunatorioNoExistenteException;

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
        return entityManager.createQuery("select e from Vacunatorio e", Vacunatorio.class)
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
}

