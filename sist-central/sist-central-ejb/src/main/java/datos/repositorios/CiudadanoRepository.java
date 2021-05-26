package datos.repositorios;

import datos.entidades.Ciudadano;
import datos.entidades.Vacunador;

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
    public void save(Ciudadano ciudadano) {
        if (findByNombreCi(ciudadano.getCi()).isEmpty())
            entityManager.persist(ciudadano);
    }
    
    public void deleteCiudadano(Ciudadano ciudadano) {
    	entityManager.remove(ciudadano);
    }
    
    @Override
    public void saveVacunador(Vacunador vacunador) {
    	if (findByNombreCi(vacunador.getCi()).isEmpty())
            entityManager.persist(vacunador);
    }

    @Override
    public List<Ciudadano> findByNombreCi(int criterio) {

        return entityManager.createQuery(
                "select c from Ciudadano c where c.ci = :cedula", Ciudadano.class)
                .setParameter("cedula",criterio)
                .getResultList();
    }
}
