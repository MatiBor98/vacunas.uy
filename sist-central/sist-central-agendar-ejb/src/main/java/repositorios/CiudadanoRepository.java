package repositorios;


import datos.entidades.Ciudadano;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Singleton
@LocalBean
public class CiudadanoRepository {
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public Optional<Ciudadano> find(int ci) {
        return entityManager.createQuery(
                "select c from Ciudadano c where c.ci = :cedula", Ciudadano.class)
                .setParameter("cedula", ci)
                .getResultList()
                .stream()
                .findFirst();
    }
}
