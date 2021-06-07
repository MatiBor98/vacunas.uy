package laboratorio.tse.configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class RecursosDeAplicacion {
    @Produces
    @PersistenceContext(unitName = "socio-logisticoPersistenceUnit")
    private EntityManager em;
}