package datos;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
@LocalBean
public class DistributedLock {
    private static final String BLOCKING_QUERY_TXT = "SELECT count(*) FROM pg_advisory_xact_lock( :lockId )";

    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Toma el lock para el id especificado y lo libera al terminar la transaccion
     */
    @Transactional( value = Transactional.TxType.MANDATORY )
    public void blockingLock(long lockId) {
        entityManager.createNativeQuery(BLOCKING_QUERY_TXT)
                .setParameter("lockId", lockId)
                .getSingleResult();
    }
}
