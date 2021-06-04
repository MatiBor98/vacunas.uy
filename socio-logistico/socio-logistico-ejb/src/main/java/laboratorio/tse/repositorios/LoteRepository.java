package laboratorio.tse.repositorios;



import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import laboratorio.tse.entidades.Lote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Singleton
public class LoteRepository implements LoteRepositoryLocal {


    @PersistenceContext(unitName = "socio-logisticoPersistenceUnit")
    private EntityManager entityManager;

    public LoteRepository() {
    }
    @Lock(LockType.READ)
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
    @Lock(LockType.READ)
	public Optional<Lote> find(int numeroLote) {
        List<Lote> resultList = entityManager.createQuery(
                "select l from Lote l where l.numeroLote = :numeroLote",
				Lote.class)
                .setParameter("numeroLote", numeroLote)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }
	
    @Lock(LockType.WRITE)
	@Override
	public void save(Lote lote) {
		entityManager.persist(lote);
		
	}
    @Lock(LockType.WRITE)
	@Override
	public void despacharLote(int numero, LocalDate fechaDespacho) {
		this.find(numero).get().setFechaDespacho(fechaDespacho);
		
	}
	
    @Lock(LockType.WRITE)
	@Override
	public void entregarLote(int numero, LocalDate fechaEntrega) {
		this.find(numero).get().setFechaEntrega(fechaEntrega);
		
	}
	
    @Lock(LockType.READ)
	@Asynchronous
    public void transportarLoteAsync(int numero) {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entregarLote(numero, LocalDate.now());
		System.out.println("Entregado");    
		
	}
    
    
    
}
