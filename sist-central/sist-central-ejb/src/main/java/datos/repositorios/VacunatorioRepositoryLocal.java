package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.Vacunatorio;

@Local
public interface VacunatorioRepositoryLocal {
	public List<Vacunatorio> find();
	public void save(Vacunatorio vac);
	public Optional<Vacunatorio> find(String nombre);
	public Optional<Vacunatorio> findWithEverything(String nombre);
}
