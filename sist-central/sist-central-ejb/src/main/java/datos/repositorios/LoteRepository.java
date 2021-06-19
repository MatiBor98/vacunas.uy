package datos.repositorios;

import datos.entidades.Lote;
import datos.entidades.Turno;
import datos.entidades.Vacunatorio;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class LoteRepository implements LoteRepositoryLocal {


    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public LoteRepository() {
    }

    @Override
	public List<Lote> find() {
        return entityManager.createQuery(
                "select l from Lote l", Lote.class)
                .getResultList();
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
	public Optional<Lote> findByNumLote(int numeroLote) {
        List<Lote> resultado = entityManager.createQuery(
                "select l from Lote l where l.numeroLote = :numeroLote", Lote.class)
                .setParameter("numeroLote", numeroLote)
                .setMaxResults(1)
                .getResultList();
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

	@Override
	public void save(Lote lote) {
		entityManager.persist(lote);
		
	}

    @Override
    public void despacharLote(int numeroLote, String socioLogistico, LocalDate fechaDespacho) {
        this.findByNumLote(numeroLote).get().setFechaDespacho(fechaDespacho);
    }

    @Override
    public void entregarLote(int numeroLote, String socioLogistico, LocalDate fechaEntrega) {
        this.findByNumLote(numeroLote).get().setFechaEntrega(fechaEntrega);

    }
}