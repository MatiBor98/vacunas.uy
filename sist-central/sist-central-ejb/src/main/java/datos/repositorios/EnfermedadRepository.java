package datos.repositorios;

import datos.entidades.Enfermedad;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Singleton
public class EnfermedadRepository {
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public EnfermedadRepository() {}

    public Optional<Enfermedad> find(String nombre) {
        List<Enfermedad> resultado = entityManager.createQuery("select e from Enfermedad e where e.nombre = :nombre", Enfermedad.class)
                .setParameter("nombre", nombre)
                .setMaxResults(1)
                .getResultList();
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }

    public List<Enfermedad> find(int primerResultado, int limiteResultados) {
        return entityManager.createQuery("select e from Enfermedad e order by e.id", Enfermedad.class)
                .setFirstResult(primerResultado)
                .setMaxResults(limiteResultados)
                .getResultList();
    }
}
