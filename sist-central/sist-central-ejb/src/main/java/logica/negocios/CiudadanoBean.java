package logica.negocios;

import datos.dtos.AgendaDTO;
import datos.dtos.CiudadanoDTO;
import datos.entidades.Agenda;
import datos.entidades.Ciudadano;
import datos.repositorios.CiudadanoRepositoryLocal;
import logica.creacion.Converter;
import logica.servicios.local.CiudadanoServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CiudadanoBean implements CiudadanoServiceLocal {
    @EJB
    private CiudadanoRepositoryLocal ciudadanoRepository;

    @Inject
    private Converter<CiudadanoDTO, Ciudadano> ciudadanoDTOConverter;

    @Inject
    private Converter<Ciudadano, CiudadanoDTO> ciudadanoConverter;

    public CiudadanoBean() {
    }

    @Override
    public List<CiudadanoDTO> find() {
        return ciudadanoRepository.find().parallelStream().map(ciudadanoConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<CiudadanoDTO> findByNombreCi(int criterio){
        return ciudadanoRepository.findByNombreCi(criterio).parallelStream().map(ciudadanoConverter::convert).collect(Collectors.toList());
    }
    
    public void updateFirebaseTokenMovil(int ci, String firebaseToken) {
    	ciudadanoRepository.findByNombreCi(ci).get(0).setFirebaseTokenMovil(firebaseToken);;
    }

    public void save(CiudadanoDTO ciudadanoDTO) {
        ciudadanoRepository.save(ciudadanoDTOConverter.convert(ciudadanoDTO));
    }

	@Override
	public void notificar(int ci) {
		Ciudadano Ciudadano = ciudadanoRepository.findByNombreCi(ci).get(0);
    	if (Ciudadano.getFirebaseTokenMovil() != null) {
    		// This registration token comes from the client FCM SDKs.
    		String registrationToken = Ciudadano.getFirebaseTokenMovil();

    		// See documentation on defining a message payload.
    		Message message = Message.builder()
    		    .putData("score", "850")
    		    .putData("time", "2:45")
    		    .setToken(registrationToken)
    		    .build();

    		// Send a message to the device corresponding to the provided
    		// registration token.
    		String response;
			try {
				response = FirebaseMessaging.getInstance().send(message);
				System.out.println("Successfully sent message: " + response);
			} catch (FirebaseMessagingException e) {
				e.printStackTrace();
			}
    		// Response is a message ID string.
    	}

		
	}
    
}