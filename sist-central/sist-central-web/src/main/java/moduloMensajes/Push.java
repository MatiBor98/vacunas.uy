package moduloMensajes;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import datos.dtos.MensajeDTO;
import logica.servicios.local.MensajeBeanLocal;

@ServerEndpoint("/push")
public class Push {

	
	@Inject
	MensajeBeanLocal mensajeBeanLocal;
	
    private static final Set<Session> SESSIONS = ConcurrentHashMap.newKeySet();

    @OnOpen
    public void onOpen(Session session) {
        SESSIONS.add(session);
        session.getAsyncRemote().sendText(mensajeBeanLocal.getMensajesJSON());
        
    }

    @OnClose
    public void onClose(Session session) {
        SESSIONS.remove(session);
    }

    @OnMessage
    public void onMessage(String message) {
    	MensajeDTO mensaje = new Gson().fromJson(message, MensajeDTO.class);
    	mensajeBeanLocal.addMensaje(mensaje);

    }
    
    public static void sendAll(String text) {
        synchronized (SESSIONS) {
            for (Session session : SESSIONS) {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(text);
                }
            }
        }
    }

}
