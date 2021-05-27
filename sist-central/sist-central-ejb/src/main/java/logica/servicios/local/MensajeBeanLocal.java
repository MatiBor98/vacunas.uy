package logica.servicios.local;

import javax.ejb.Local;

import datos.dtos.MensajeDTO;

@Local
public interface MensajeBeanLocal {
	void addMensaje(MensajeDTO mensaje);
	String getMensajesJSON();
}
