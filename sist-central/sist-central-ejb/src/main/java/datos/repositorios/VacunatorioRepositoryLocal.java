package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.Vacunatorio;

@Local
public interface VacunatorioRepositoryLocal {
	 List<Vacunatorio> find();
	 void save(Vacunatorio vac);
	 Optional<Vacunatorio> find(String nombre);
	 Optional<Vacunatorio> findWithEverything(String nombre);
}
