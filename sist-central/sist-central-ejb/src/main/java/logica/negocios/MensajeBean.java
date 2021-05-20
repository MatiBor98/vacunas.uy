package logica.negocios;

import java.time.LocalDateTime;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import datos.entidades.Mensaje;
import datos.entidades.Vacunador;
import datos.repositorios.MensajeRepositoryLocal;

/**
 * Session Bean implementation class MensajeBean
 */
@Stateless
@LocalBean
public class MensajeBean implements MensajeBeanLocal {

	@EJB
	MensajeRepositoryLocal mensajes;
	
    /**
     * Default constructor. 
     */
    public MensajeBean() {
        // TODO Auto-generated constructor stub
    }
    
    public void addMensaje(String text, LocalDateTime fechaHora, Vacunador vac) {
    	Mensaje msg = new Mensaje();
    	msg.setFechaHora(LocalDateTime.now());
    	msg.setVacunador(vac);
    	msg.setMensaje(text);
    	mensajes.addMensaje(msg);
    }

}
