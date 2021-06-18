package datos.repositorios;

import datos.entidades.*;
import datos.exceptions.CiudadanoRegistradoException;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@LocalBean
public class CiudadanoRepository {


    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
	private EntityManager entityManager;

    public CiudadanoRepository() {
    }

    public List<Ciudadano> find() {
        return entityManager.createQuery("SELECT c FROM Ciudadano c", Ciudadano.class).getResultList();
    }

    public void save(Ciudadano ciudadano) throws CiudadanoRegistradoException {
        if (findByNombreCi(ciudadano.getCi()) == null)  {
            entityManager.persist(ciudadano);
        }
        else {
        	throw new CiudadanoRegistradoException();
        }
        	
    }

    public void deleteCiudadano(Ciudadano ciudadano) {
    	entityManager.remove(ciudadano);
    }

    public void saveVacunador(Vacunador vacunador) {
    	if (findByNombreCi(vacunador.getCi()) == null)
            entityManager.persist(vacunador);
    }

    public Ciudadano findByNombreCi(int criterio) {
    	return entityManager.find(Ciudadano.class, criterio);
    }

    public void ciudadanoToVacunador(String ci) {
    	entityManager.createNativeQuery("UPDATE ciudadano "
    										+ "SET rol = 'Vacunador' "
    											+ "WHERE ci = " + ci + ";").executeUpdate();
    }

    public void vacunadorToCiudadano(String ci) {
    	entityManager.createNativeQuery("UPDATE ciudadano "
    										+ "SET rol = 'Ciudadano' "
    											+ "WHERE ci = " + ci + ";").executeUpdate();
    }

    public Optional<Ciudadano> find(int ci) {
        return entityManager.createQuery(
                "select c from Ciudadano c where c.ci = :cedula", Ciudadano.class)
                .setParameter("cedula", ci)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Ciudadano getCiudadanoConReservas(int ci){
        Ciudadano ciudadano = entityManager.find(Ciudadano.class, ci);
        List<Intervalo> intervalos = ciudadano.getReservas().values().parallelStream().map(Reserva::getIntervalo).collect(Collectors.toList());
        List<Vacuna> vacunaList = intervalos.parallelStream().map(Intervalo::getAgenda).map(Agenda::getEtapa)
                .map(Etapa::getVacuna).collect(Collectors.toList());
        vacunaList.parallelStream().map(Vacuna::getEnfermedades).collect(Collectors.toList()).size();
        return  ciudadano;

    }

    public List<Ciudadano> findTokenNotNull() {
        return entityManager.createQuery(
                "select c from Ciudadano c where c.firebaseTokenMovil is not null", Ciudadano.class)
                .getResultList();
    }

}
