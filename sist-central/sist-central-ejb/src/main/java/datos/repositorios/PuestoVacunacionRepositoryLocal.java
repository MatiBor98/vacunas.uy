package datos.repositorios;

import datos.entidades.Asignacion;
import datos.entidades.PuestoVacunacion;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface PuestoVacunacionRepositoryLocal {
	List<PuestoVacunacion> find();
	void save(PuestoVacunacion puesto);
	Optional<PuestoVacunacion> find(int id);
	List<PuestoVacunacion> find(String vacunatorio, String nombrePuesto);
	List<Asignacion> getAsignaciones(String nombreVacunatorio, String nombrePuesto);
	void addAsignacion(Asignacion asig);
}
