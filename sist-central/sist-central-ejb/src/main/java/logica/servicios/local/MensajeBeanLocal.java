package logica.servicios.local;

import datos.dtos.MensajeDTO;

import javax.ejb.Local;

@Local
public interface MensajeBeanLocal {
	void addMensaje(MensajeDTO mensaje);
	String getMensajesJSON();
}
