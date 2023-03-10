package datos.repositorios;

import datos.entidades.Enfermedad;
import datos.entidades.Etapa;
import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class VacunaRepository implements VacunaRepositoryLocal {
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public VacunaRepository() {
    }

    @Override
    public List<Vacuna> find() {
    	Query query = entityManager.createQuery("SELECT v FROM Vacuna v");
    	List<Vacuna> vacs = query.getResultList();
    	return vacs;
    }

    public Optional<Vacuna> find(String nombre) {
        List<Vacuna> resultado = entityManager.createQuery("select v from Vacuna v where v.nombre = :nombre", Vacuna.class)
                .setParameter("nombre", nombre)
                .setMaxResults(1)
                .getResultList();
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    @Override
    public List<Vacuna> findByNombreVacuna(String nombreVac) {
    	Query query = entityManager.createQuery("SELECT v FROM Vacuna v WHERE lower(v.nombre) = :nombreVac").setParameter("nombreVac", nombreVac.toLowerCase());
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

    @Override
    public List<Vacuna> findByEnfermedad(String enfermedad) {

        Enfermedad enfermedadObj = entityManager.find(Enfermedad.class, enfermedad);

        List<Vacuna> vacunas = enfermedadObj.getVacunas();

        vacunas.stream().map(Vacuna::getEtapas).collect(Collectors.toList()).size();

        return vacunas;
    }

    @Override
    public List<Etapa> getEtapas(String nombreVacuna) {
        Vacuna vac = entityManager.find(Vacuna.class, nombreVacuna);
        vac.getEtapas().size();
        return vac.getEtapas();

    }
}