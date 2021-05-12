package logica.servicios.local;

import javax.ejb.Local;

@Local
public interface PuestoVacunacionBeanLocal {

	public long addPuestoVacunacion(String nombrePuesto, String nombreVacunatorio);
}
