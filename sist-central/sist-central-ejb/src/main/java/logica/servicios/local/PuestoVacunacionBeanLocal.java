package logica.servicios.local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.PuestoVacunacion;
import datos.entidades.Vacunatorio;

@Local
public interface PuestoVacunacionBeanLocal {

	int addPuestoVacunacion(String nombrePuesto, String nombreVacunatorio);
	public Optional<PuestoVacunacion> find(int id);
	public List<PuestoVacunacion> find();
	public List<PuestoVacunacion> find(String vac, String nomPuesto);
}
