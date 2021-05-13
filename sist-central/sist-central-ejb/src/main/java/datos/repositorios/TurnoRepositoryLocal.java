package datos.repositorios;

import java.util.List;
import java.util.Optional;

import datos.entidades.Turno;

public interface TurnoRepositoryLocal {

	List<Turno> find();

	Optional<Turno> findById(long id);

	void save(Turno turno);

}