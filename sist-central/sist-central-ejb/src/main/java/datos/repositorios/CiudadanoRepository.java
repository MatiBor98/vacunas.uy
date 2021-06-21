package datos.repositorios;

import datos.entidades.*;
import datos.exceptions.CiudadanoRegistradoException;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Singleton
public class CiudadanoRepository implements CiudadanoRepositoryLocal{


    @PersistenceContext(unitName = "sist-centralPersistenceUnit")
	private EntityManager entityManager;

    public CiudadanoRepository() {
    }

    @Override
    public List<Ciudadano> find() {
        return entityManager.createQuery("SELECT c FROM Ciudadano c", Ciudadano.class).getResultList();
    }

    @Override
    public void save(Ciudadano ciudadano) throws CiudadanoRegistradoException {
        if (findByNombreCi(ciudadano.getCi()) == null)  {
            entityManager.persist(ciudadano);
        }
        else {
        	throw new CiudadanoRegistradoException();
        }
        	
    }
    
    @Override
    public void deleteCiudadano(Ciudadano ciudadano) {
    	entityManager.remove(ciudadano);
    }
    
    @Override
    public void saveVacunador(Vacunador vacunador) {
    	if (findByNombreCi(vacunador.getCi()) == null)
            entityManager.persist(vacunador);
    }

    @Override
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

    @Override
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

    @Override
    public List<Ciudadano> findTokenNotNull() {
        return entityManager.createQuery(
                "select c from Ciudadano c where c.firebaseTokenMovil is not null", Ciudadano.class)
                .getResultList();
    }

	@Override
	public List<Asignacion> findAsignacionesVacunador(int cid) {
		return entityManager.createQuery(
                "select a from Asignacion a where a.ciVacunador = :cedula", Asignacion.class)
				.setParameter("cedula", cid)
                .getResultList();
	}

}
