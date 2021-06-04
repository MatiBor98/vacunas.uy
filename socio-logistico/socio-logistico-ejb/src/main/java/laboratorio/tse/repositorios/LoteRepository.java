package laboratorio.tse.repositorios;



import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import laboratorio.tse.entidades.Lote;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class LoteRepository implements LoteRepositoryLocal {


    @PersistenceContext(unitName = "socio-logisticoPersistenceUnit")
    private EntityManager entityManager;

    public LoteRepository() {
    }

	@Override
	public List<Lote> find() {
		List<Lote> res = entityManager.createQuery(
                "select l from Lote l", Lote.class)
                .getResultList();
        return res;
    }

    /*@Override
    public List<Lote> find(String vac, String nombreLote) {
        List<Vacunatorio> resultList = entityManager.createQuery(
                "select e from Vacunatorio e where e.nombre = :vac",Vacunatorio.class)
                .setParameter("vac", vac)
                .setMaxResults(1)
                .getResultList();

        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }*/


	@Override
	public void save(Lote lote) {
		entityManager.persist(lote);
		
	}
	

    
    
    
    
}
