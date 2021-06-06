package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Remote;

import datos.entidades.Turno;

@Remote
public interface TurnoRepositoryRemote {

	List<Turno> find();
	Optional<Turno> findById(int id);
	void save(Turno turno);
	public List<Turno> find(String vac, String nombreTurno);
	void drop();

}