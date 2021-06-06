package logica.servicios.local;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.dtos.PuestoVacunacionDTO;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Turno;
import datos.entidades.Vacunador;
import datos.entidades.Vacunatorio;

@Local
public interface PuestoVacunacionBeanLocal {

	int addPuestoVacunacion(String nombrePuesto, String nombreVacunatorio);
	public Optional<PuestoVacunacion> find(int id);
	public List<PuestoVacunacion> find();
	public List<PuestoVacunacion> find(String vac, String nomPuesto);
	List<Vacunador> getVacunadoresNoAsignados(String nombreVacunatorio, String nombrePuesto);
	void addAsignacion(Vacunador vac, Turno turn, PuestoVacunacion pVac, LocalDate localInicio, LocalDate localFin);
	List<PuestoVacunacionDTO> getDTO(Vacunatorio vac);
}
