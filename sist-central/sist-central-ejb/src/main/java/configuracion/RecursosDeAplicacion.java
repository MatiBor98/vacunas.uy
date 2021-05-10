package configuracion;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class RecursosDeAplicacion {
    @Produces
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager em;
}