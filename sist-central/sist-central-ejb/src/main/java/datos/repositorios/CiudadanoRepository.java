package datos.repositorios;

import datos.entidades.Agenda;
import datos.entidades.Ciudadano;

import javax.ejb.Singleton;
import javax.inject.Inject;
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
        return entityManager.createQuery("SELECT c FROM Ciudadano c").getResultList();
    }

    @Override
    public void save(Ciudadano ciudadano) {
        entityManager.persist(ciudadano);
    }

    @Override
    public List<Ciudadano> findByNombreCi(int criterio) {

        return entityManager.createQuery(
                "select c from Ciudadano c where c.ci = :cedula")
                .setParameter("cedula",criterio)
                .getResultList();
    }
}
