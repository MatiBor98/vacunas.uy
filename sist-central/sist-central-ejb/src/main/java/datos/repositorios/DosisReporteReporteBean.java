package datos.repositorios;

import datos.entidades.Ciudadano;
import datos.entidades.Estado;
import datos.entidades.Reserva;
import datos.entidades.reporteStockDosis.DatosDosis;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<DatosDosis> findDatos(String enfermedad, String vacuna, String vacunatorio){
        return findDatos( enfermedad,  vacuna,  vacunatorio, LocalDate.of(2021,1,1), LocalDate.of(2021,7,1));
    }


    public List<DatosDosis> findDatos(String enfermedad, String vacuna, String vacunatorio, LocalDate comienzo, LocalDate fin){

        if (comienzo == null || fin == null){
            return findDatos(enfermedad,vacuna,vacunatorio);
        }

        String qlString = " select d from DatosDosis d " +
                "join Vacuna v on (d.nombreVacuna = v.nombre)" +
                "join v.enfermedades enf "
                + "where d.fecha >= :comienzo "
                + "and d.fecha <= :fin ";
        if (!enfermedad.equals(""))
            qlString += " and enf.nombre = :enfermedad ";
        if (!vacuna.equals(""))
            qlString += " and d.nombreVacuna = :vacuna ";
        if (!vacunatorio.equals(""))
            qlString += " and d.nombreVacunatorio = :vacunatorio ";

        Query query = entityManager.createQuery(
                qlString)
                .setParameter("comienzo", comienzo)
                .setParameter("fin", fin);

        if (!enfermedad.equals(""))
            query.setParameter("enfermedad", enfermedad);
        if (!vacuna.equals(""))
            query.setParameter("vacuna", vacuna);
        if (!vacunatorio.equals(""))
            query.setParameter("vacunatorio", vacunatorio);


        return query.getResultList();
    }



}
