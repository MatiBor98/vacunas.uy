package datos.repositorios;

import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

@Singleton
public class LaboratorioRepository implements LaboratorioRepositoryLocal {
   
	@PersistenceContext
    private EntityManager entityManager;

    public LaboratorioRepository() {
    }

    @Override
    public List<Laboratorio> find() {
    	Query query = entityManager.createQuery("SELECT l FROM Laboratorio l");
    	List<Laboratorio> labs = query.getResultList();
    	return labs;
    }
    @Override
    public List<Laboratorio> findByNombreLaboratorio(String nombreLab) {
    	Query query = entityManager.createQuery("SELECT l FROM Laboratorio l WHERE lower(l.nombre) like :nombreLab").setParameter("nombreLab", "%" + nombreLab.toLowerCase() + "%");
    	List<Laboratorio> labs = query.getResultList();
    	return labs;
    }

    @Override
   
    public void save(String nombre, List<Vacuna> vacs) {
    	Laboratorio lab = new Laboratorio(nombre, vacs);
        entityManager.persist(lab);
    }
    
    public void eliminar(String nombre) {
    	Laboratorio lab = new Laboratorio();
    	lab = entityManager.find(Laboratorio.class, nombre);
    	entityManager.remove(lab);
    }
}