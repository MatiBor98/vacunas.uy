package datos.repositorios;

import datos.entidades.reporteStockDosis.DatosDosis;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class DosisReporteReporteBean {
    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public DosisReporteReporteBean() {
    }

    public List<DatosDosis> find(){
      return entityManager.createQuery("SELECT c FROM DatosDosis c", DatosDosis.class).getResultList();
    }

    public void save(DatosDosis dat){
      entityManager.persist(dat);
    }

    public void saveAll(List<DatosDosis> datosDosisList){
        for (DatosDosis dato: datosDosisList){
            entityManager.persist(dato);
        }
    }

}
