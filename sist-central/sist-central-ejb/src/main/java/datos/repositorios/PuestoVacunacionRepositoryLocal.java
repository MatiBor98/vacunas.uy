package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.PuestoVacunacion;
import datos.entidades.Vacunatorio;

@Local
public interface PuestoVacunacionRepositoryLocal {
	List<PuestoVacunacion> find();
	void save(PuestoVacunacion puesto);
	Optional<PuestoVacunacion> find(int id);
	List<PuestoVacunacion> find(String vacunatorio, String nombrePuesto);
}
