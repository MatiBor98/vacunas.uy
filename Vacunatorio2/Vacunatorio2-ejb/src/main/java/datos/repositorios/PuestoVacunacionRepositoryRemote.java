package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;
import javax.ejb.Remote;

import datos.entidades.Asignacion;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Vacunatorio;

@Remote
public interface PuestoVacunacionRepositoryRemote {
	List<PuestoVacunacion> find();
	void save(PuestoVacunacion puesto);
	Optional<PuestoVacunacion> find(int id);
	List<PuestoVacunacion> find(String vacunatorio, String nombrePuesto);
	List<Asignacion> getAsignaciones(String nombreVacunatorio, String nombrePuesto);
	void addAsignacion(Asignacion asig);
	void drop();
	void dropAsignaciones();
}
