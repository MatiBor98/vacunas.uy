package datos.repositorios;

import datos.entidades.Enfermedad;
import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

@Singleton
public class VacunaRepository implements VacunaRepositoryLocal {
    @Inject
    private EntityManager entityManager;

    public VacunaRepository() {
    }

    @Override
    public List<Vacuna> find() {
    	Query query = entityManager.createQuery("SELECT v FROM Vacuna v");
    	List<Vacuna> vacs = query.getResultList();
    	return vacs;
    }
    @Override
    public List<Vacuna> findByNombreVacuna(String nombreVac) {
    	Query query = entityManager.createQuery("SELECT v FROM Vacuna v WHERE lower(v.nombre) like :nombreVac").setParameter("nombreVac", "%" + nombreVac.toLowerCase() + "%");
    	List<Vacuna> vacs = query.getResultList();
    	return vacs;
    }

    @Override
   
    public void save(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, List<Laboratorio>labs, List<Enfermedad>enfs) {
    	Vacuna vac = new Vacuna(labs, enfs, nombre, cantDosis, inmunidadMeses, dosisSeparacion);
        entityManager.persist(vac);
    }
    
    public void eliminar(String nombre) {
    	Vacuna vac = new Vacuna();
    	vac = entityManager.find(Vacuna.class, nombre);
    	entityManager.remove(vac);
    }
    
    public void modificarVacuna(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, List<Laboratorio>labs, List<Enfermedad>enfs) {
    	Vacuna vacAModificar = findByNombreVacuna(nombre).get(0);
    	vacAModificar.setCantDosis(cantDosis);
    	vacAModificar.setInmunidadMeses(inmunidadMeses);
    	vacAModificar.setDosisSeparacionDias(dosisSeparacion);
    	vacAModificar.setLaboratorios(labs);
    	vacAModificar.setEnfermedades(enfs);
    }
}