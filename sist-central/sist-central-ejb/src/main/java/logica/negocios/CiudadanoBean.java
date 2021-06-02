package logica.negocios;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import datos.dtos.CiudadanoDTO;
import datos.entidades.Ciudadano;
import datos.entidades.Vacunador;
import datos.exceptions.CiudadanoNoEncontradoException;
import datos.exceptions.CiudadanoRegistradoException;
import datos.repositorios.CiudadanoRepositoryLocal;
import logica.creacion.Converter;
import logica.servicios.local.CiudadanoServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
    public CiudadanoDTO findByNombreCi(int criterio) throws CiudadanoNoEncontradoException{
    	Ciudadano buscado = ciudadanoRepository.findByNombreCi(criterio);
    	if(buscado == null) {
    		throw new CiudadanoNoEncontradoException();
    	}
        return ciudadanoConverter.convert(buscado);
    }


    public void updateFirebaseTokenMovil(int ci, String firebaseToken) {
    	ciudadanoRepository.findByNombreCi(ci).setFirebaseTokenMovil(firebaseToken);;
    }

    public void save(CiudadanoDTO ciudadanoDTO) throws CiudadanoRegistradoException{
        ciudadanoRepository.save(ciudadanoDTOConverter.convert(ciudadanoDTO));
    }

    @Override
    public void overwriteCiudadano(CiudadanoDTO ciudadano) {
    	Ciudadano userLegacy = ciudadanoRepository.findByNombreCi(ciudadano.getCi());
    	Ciudadano userNew =ciudadanoDTOConverter.convert(ciudadano);
    	if(!(userLegacy instanceof Vacunador) && (userNew instanceof Vacunador)) {
    		ciudadanoRepository.ciudadanoToVacunador(String.valueOf(userNew.getCi()));
    	}
    	else if(userLegacy instanceof Vacunador && !(userNew instanceof Vacunador)) {
    		ciudadanoRepository.vacunadorToCiudadano(String.valueOf(userNew.getCi()));
    	}
    	userLegacy.setEmail(userNew.getEmail());
    	userLegacy.setNombre(userNew.getNombre());
    }

	@Override
	public void notificar(int ci) {
		Ciudadano Ciudadano = ciudadanoRepository.findByNombreCi(ci);
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

	@Override
	public Vacunador findVacunador(int ciVac) {
		Ciudadano ciud = ciudadanoRepository.findByNombreCi(ciVac);
		Vacunador vac = null;
		if (ciud instanceof Vacunador) {
			vac = (Vacunador) ciud;
		}
		return vac;
	}

}