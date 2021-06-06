package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.Turno;

@Local
public interface TurnoRepositoryLocal {

	List<Turno> find();
	Optional<Turno> findById(int id);
	void save(Turno turno);
	public List<Turno> find(String vac, String nombreTurno);
	void drop();

}