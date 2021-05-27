package datos.repositorios;

import datos.entidades.Departamento;
import datos.entidades.SocioLogistico;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Session Bean implementation class VacunatorioRepository
 */
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

    public void save(SocioLogistico soc) {
        entityManager.persist(soc);
    }

    @Override
    public void habilitar(String nombre) {
        this.find(nombre).get().setHabilitado(true);
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

