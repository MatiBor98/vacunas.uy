package datos.repositorios;

import datos.entidades.Ciudadano;
import datos.entidades.Vacunador;
import datos.exceptions.CiudadanoRegistradoException;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class CiudadanoRepository implements CiudadanoRepositoryLocal{


    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
	private EntityManager entityManager;

    public CiudadanoRepository() {
    }

    @Override
    public List<Ciudadano> find() {
        return entityManager.createQuery("SELECT c FROM Ciudadano c", Ciudadano.class).getResultList();
    }

    @Override
    public void save(Ciudadano ciudadano) throws CiudadanoRegistradoException {
        if (findByNombreCi(ciudadano.getCi()) == null)  {
            entityManager.persist(ciudadano);
        }
        else {
        	throw new CiudadanoRegistradoException();
        }
        	
    }
    
    @Override
    public void deleteCiudadano(Ciudadano ciudadano) {
    	entityManager.remove(ciudadano);
    }
    
    @Override
    public void saveVacunador(Vacunador vacunador) {
    	if (findByNombreCi(vacunador.getCi()) == null)
            entityManager.persist(vacunador);
    }

    @Override
    public Ciudadano findByNombreCi(int criterio) {

        /*return entityManager.createQuery(
                "select c from Ciudadano c where c.ci = :cedula", Ciudadano.class)
                .setParameter("cedula",criterio)
                .getResultList();*/
    	return entityManager.find(Ciudadano.class, criterio);
    }
    

    public void ciudadanoToVacunador(String ci) {
    	entityManager.createNativeQuery("UPDATE ciudadano "
    										+ "SET rol = 'Vacunador' "
    											+ "WHERE ci = " + ci + ";").executeUpdate();
    }
    
    public void vacunadorToCiudadano(String ci) {
    	entityManager.createNativeQuery("UPDATE ciudadano "
    										+ "SET rol = 'Ciudadano' "
    											+ "WHERE ci = " + ci + ";").executeUpdate();
    }
    
}
