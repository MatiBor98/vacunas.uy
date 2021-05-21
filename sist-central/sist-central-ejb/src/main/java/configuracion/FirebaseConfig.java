package configuracion;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import datos.dtos.CiudadanoDTO;
import logica.creacion.CiudadanoDTOBuilder;
import logica.servicios.local.CiudadanoServiceLocal;

@Singleton
@Startup
public class FirebaseConfig {

	private static final String PATH_TO_SERVICE_ACCOUNT_KEY_JSON = "C:/vacunas-uy-firebase-adminsdk.json";

	public FirebaseConfig() {

		try {

			FileInputStream serviceAccount = new FileInputStream(PATH_TO_SERVICE_ACCOUNT_KEY_JSON);
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
			FirebaseApp.initializeApp(options);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	@EJB
	CiudadanoServiceLocal ciudadanoServiceLocal;
	@PostConstruct
	public void prueba() {
		
		ciudadanoServiceLocal.save(new CiudadanoDTOBuilder()
				.setCi(50550419).setNombre("Nicolás").setEmail("email@").createCiudadanoDTO());
	}

}
