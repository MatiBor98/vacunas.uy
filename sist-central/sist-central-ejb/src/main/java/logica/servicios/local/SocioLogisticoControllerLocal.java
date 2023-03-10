package logica.servicios.local;

import datos.entidades.Departamento;
import datos.entidades.Lote;
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
	List<SocioLogistico> findHabilitados();
	void addSocioLogistico(String nombre);
	void habilitarSocioLogistico(String nombre);
	String getProperty();
	void setProperty(String property);
	List<Lote> getLotes(String nomSocLog);

	}
