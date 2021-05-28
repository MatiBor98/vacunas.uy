package logica.negocios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import datos.dtos.MensajeDTO;
import datos.entidades.Mensaje;
import datos.entidades.Vacunador;
import datos.repositorios.MensajeRepositoryLocal;
import logica.servicios.local.MensajeBeanLocal;

/**
 * Session Bean implementation class MensajeBean
 */
@Stateless
@LocalBean
public class MensajeBean implements MensajeBeanLocal {

	
    /**
     * Default constructor. 
     */
    public MensajeBean() {
        // TODO Auto-generated constructor stub
    }
    
    public void addMensaje(MensajeDTO mensaje) {
    	DatabaseReference databaseRef = FirebaseDatabase
    		    .getInstance()
    		    .getReference("/mensajes");
    	
    	
    	databaseRef = databaseRef.child("chatGeneral");
    	
    	
    	DatabaseReference chatGeneralRef = databaseRef.push();

    	chatGeneralRef.setValueAsync(mensaje);	
    }
    
    public List<MensajeDTO> getMensajes(){
    	
    	final Semaphore semaphore = new Semaphore(0);
    	
    	DatabaseReference ref = FirebaseDatabase.getInstance()
			    .getReference("/mensajes/chatGeneral");
    	
    	ArrayList<List<MensajeDTO>> aux = new ArrayList<List<MensajeDTO>>();
    	
    	ref.addListenerForSingleValueEvent(new ValueEventListener() {
    		  @Override
    		  public void onDataChange(DataSnapshot dataSnapshot) {
    			try {
    				GenericTypeIndicator<Map<String,MensajeDTO>> tLista = new GenericTypeIndicator<Map<String,MensajeDTO>>() {};
        			List<MensajeDTO> mensajes = new ArrayList<MensajeDTO>( dataSnapshot.getValue(tLista).values());
      
        		    Collections.sort(mensajes);

        			aux.add(mensajes);
				} catch (Exception e) {
					e.printStackTrace();
				}
    			
    	        semaphore.release();
    		  }

    		  @Override
    		  public void onCancelled(DatabaseError databaseError) {
    		  }
    		});
    	
    	try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aux.get(0);
    }
    
    
    public String getMensajesJSON() {
    	List<MensajeDTO> mensajes = getMensajes();
    	Gson gson = new Gson();
    	return gson.toJson(mensajes);
    }

}
