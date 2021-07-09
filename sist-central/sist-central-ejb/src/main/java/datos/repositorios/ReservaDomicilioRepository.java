package datos.repositorios;

import datos.entidades.ReservaDomicilio;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Singleton
@LocalBean
public class ReservaDomicilioRepository {

    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
    private EntityManager entityManager;

    public void saveReservaDomicilio(ReservaDomicilio reserva) {
        entityManager.persist(reserva);
    }

    public List<ReservaDomicilio> findReservasADomicilioCiudadano(int offset, int limit, int ci){

        return entityManager.createQuery(
                "select r from ReservaDomicilio r " +
                        "where r.ciudadano.ci = :ci "
                , ReservaDomicilio.class)
                .setParameter("ci", ci)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public List<ReservaDomicilio> findReservasADomicilio(int offset, int limit){

        return entityManager.createQuery(
                "select r from ReservaDomicilio r "
                , ReservaDomicilio.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public Optional<ReservaDomicilio> findReservaADomicilio(int codigo){

        return entityManager.createQuery(
                "select r from ReservaDomicilio r where r.codigo = :codigo", ReservaDomicilio.class)
                .setParameter("codigo", codigo)
                .getResultList()
                .stream()
                .findFirst();
    }


}
