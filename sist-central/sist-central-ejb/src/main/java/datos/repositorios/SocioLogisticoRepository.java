package datos.repositorios;

import datos.entidades.Lote;
import datos.entidades.SocioLogistico;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Singleton
public class SocioLogisticoRepository implements SocioLogisticoRepositoryLocal {

    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public SocioLogisticoRepository() {
    }

    public List<SocioLogistico> find() {
        return entityManager.createQuery("select s from SocioLogistico s", SocioLogistico.class)
                .getResultList();
    }

    @Override
    public List<SocioLogistico> findHabilitados() {
        return entityManager.createQuery("select s from SocioLogistico s where s.habilitado = :habilitado",SocioLogistico.class)
                .setParameter("habilitado", true)
                .getResultList();
    }

    public void save(SocioLogistico soc) {
        entityManager.persist(soc);
    }

    @Override
    public void habilitar(String nombre) {
        this.find(nombre).get().setHabilitado(true);
    }

    @Override
    public List<Lote> getLotes(String nombreSocioLogistico) {
        SocioLogistico socLog = find(nombreSocioLogistico).get();
        return socLog.getLotes();
    }

    public Optional<SocioLogistico> find(String nombre) {
        List<SocioLogistico> resultList = entityManager.createQuery(
                "select s from SocioLogistico s where s.nombre = :nombre",
				SocioLogistico.class)
                .setParameter("nombre", nombre)
                .setMaxResults(1)
                .getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

}

