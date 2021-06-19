package configuracion;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import datos.dtos.MensajeDTO;
import datos.dtos.MensajesChangedEvent;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@Singleton
@Startup
public class FirebaseConfig {

    @Inject
    private BeanManager beanManager;


    public FirebaseConfig() {

    }

    @PostConstruct
    private void initFirebase() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream("META-INF/vacunas-uy-firebase-adminsdk.json");


            //FileInputStream serviceAccount = new FileInputStream(PATH_TO_SERVICE_ACCOUNT_KEY_JSON);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setDatabaseUrl("https://vacunas-uy-default-rtdb.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);

            initializeDatabase();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initializeDatabase() {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("/mensajes/chatGeneral");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				  /*
				  GenericTypeIndicator<List<MensajeDTO>> tListaMensajes = new GenericTypeIndicator<List<MensajeDTO>>() {};
				  List<MensajeDTO> mensajes = dataSnapshot.getValue(tListaMensajes);
				  beanManager.fireEvent(new MensajesChangedEvent(mensajes));*/

                MensajeDTO mensaje = dataSnapshot.getValue(MensajeDTO.class);
                beanManager.fireEvent(new MensajesChangedEvent(mensaje));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}
