package moduloMensajes;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import com.google.gson.Gson;

import datos.dtos.MensajesChangedEvent;


@ApplicationScoped
public class MensajesBean {


	    public void onMenuChange(@Observes MensajesChangedEvent event) {
	    	Gson gson = new Gson();

	    	String jsonMensaje = gson.toJson(event.getMensaje());
	        Push.sendAll(jsonMensaje);
	    }

	}

