package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.PuestoVacunacion;

@Local
public interface PuestoVacunacionRepositoryLocal {
	public List<PuestoVacunacion> find();
	public void save(PuestoVacunacion puesto);
	public Optional<PuestoVacunacion> find(long id);
}
