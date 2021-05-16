package logica.servicios.local;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.ejb.Local;

@Local
public interface PuestoVacunacionBeanLocal {

	int addPuestoVacunacion(String nombrePuesto, String nombreVacunatorio);
}
