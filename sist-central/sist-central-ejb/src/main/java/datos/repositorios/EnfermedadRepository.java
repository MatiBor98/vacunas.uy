package datos.repositorios;

import datos.entidades.Enfermedad;
import datos.entidades.Laboratorio;
import datos.entidades.PlanVacunacion;
import datos.entidades.Vacuna;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;
import java.util.Optional;

@Singleton
public class EnfermedadRepository implements EnfermedadRepositoryLocal {
   
	@PersistenceContext
    private EntityManager entityManager;

    public EnfermedadRepository() {
    }

    @Override
    public List<Enfermedad> find() {
    	return entityManager.createQuery("SELECT e FROM Enfermedad e", Enfermedad.class).getResultList();
    }

    @Override
    public List<Enfermedad> find(int primerResultado, int limiteResultados) {
        return entityManager.createQuery("select e from Enfermedad e " +
                "order by e.id", Enfermedad.class)
                .setFirstResult(primerResultado)
                .setMaxResults(limiteResultados)
                .getResultList();
    }

    public Optional<Enfermedad> find(String nombre) {
        List<Enfermedad> resultado = entityManager.createQuery("select e from Enfermedad e where e.nombre = :nombre", Enfermedad.class)
                .setParameter("nombre", nombre)
                .setMaxResults(1)
                .getResultList();
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    public List<Enfermedad> find(int primerResultado, int limiteResultados, String criterio) {
        return entityManager.createQuery("select e from Enfermedad e " +
                "where lower(e.nombre) like lower(:criterio) " +
                "order by e.id", Enfermedad.class)
                .setParameter("criterio", "%" + criterio + "%")
                .setFirstResult(primerResultado)
                .setMaxResults(limiteResultados)
                .getResultList();
    }
    
    @Override
    public List<Enfermedad> findByNombreEnfermedad(String nombreEnf) {
    	Query query = entityManager.createQuery("SELECT e FROM Enfermedad e WHERE lower(e.nombre) = :nombreEnf").setParameter("nombreEnf", nombreEnf.toLowerCase());
    	List<Enfermedad> enfs = query.getResultList();
    	return enfs;
    }

    @Override
   
    public void save(String nombre, String descripcion, List<Vacuna> vacunas, List<PlanVacunacion> planesVacunacion) {
    	Enfermedad enf = new Enfermedad(nombre, descripcion, vacunas, planesVacunacion);
        entityManager.persist(enf);
    }
    
    public void eliminar(String nombre) {
    	Enfermedad enf = new Enfermedad();
    	enf = entityManager.find(Enfermedad.class, nombre);
    	entityManager.remove(enf);
    }
}



