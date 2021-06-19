package datos.repositorios;

import datos.entidades.Turno;

import java.util.List;
import java.util.Optional;

public interface TurnoRepositoryLocal {

	List<Turno> find();
	Optional<Turno> findById(int id);
	void save(Turno turno);
	public List<Turno> find(String vac, String nombreTurno);

}