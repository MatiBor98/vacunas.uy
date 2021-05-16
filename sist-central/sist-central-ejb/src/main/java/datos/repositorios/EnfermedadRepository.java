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

@Singleton
public class EnfermedadRepository implements EnfermedadRepositoryLocal {
   
	@PersistenceContext
    private EntityManager entityManager;

    public EnfermedadRepository() {
    }

    @Override
    public List<Enfermedad> find() {
    	Query query = entityManager.createQuery("SELECT e FROM Enfermedad e");
    	List<Enfermedad> enfs = query.getResultList();
    	return enfs;
    }
    @Override
    public List<Enfermedad> findByNombreEnfermedad(String nombreEnf) {
    	Query query = entityManager.createQuery("SELECT e FROM Enfermedad e WHERE lower(e.nombre) like :nombreEnf").setParameter("nombreEnf", "%" + nombreEnf.toLowerCase() + "%");
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