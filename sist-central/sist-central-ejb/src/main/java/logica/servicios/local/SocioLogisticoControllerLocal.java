package logica.servicios.local;

import datos.entidades.Departamento;
import datos.entidades.SocioLogistico;
import datos.entidades.Vacunatorio;

import javax.ejb.Local;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Local
public interface SocioLogisticoControllerLocal {
	Optional<SocioLogistico> find(String nombre);
	List<SocioLogistico> find();
	void addSocioLogistico(String nombre);
	void habilitarSocioLogistico(String nombre);

	}
