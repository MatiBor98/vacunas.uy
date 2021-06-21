package logica.negocios;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import datos.dtos.CiudadanoDTO;
import datos.dtos.VacunadorDTO;
import datos.entidades.Asignacion;
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

import plataformainteroperabilidad.Sexo;
import plataformainteroperabilidad.Trabajo;


import java.time.LocalDate;
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
	public List<CiudadanoDTO> findTokenNotNull() {
		return ciudadanoRepository.findTokenNotNull().parallelStream().map(ciudadanoConverter::convert).collect(Collectors.toList());
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

    public void setSexoFechanacimiento(Integer ci, Sexo sexo, LocalDate fechaNacimiento, Trabajo trabajo){
		ciudadanoRepository.find(ci).ifPresent(ciudadano -> {
			ciudadano.setSexo(sexo);
			ciudadano.setFechaNacimiento(fechaNacimiento);
			ciudadano.setTrabajo(trabajo);
		});
	}


    @Override
    public void notificarTodosLosUsuariosMoviles(String titulo, String cuerpo) {
        for (Ciudadano ciudadano : ciudadanoRepository.findTokenNotNull()){
			notificar(ciudadano.getFirebaseTokenMovil(),titulo,cuerpo);
		}
    }

	@Override
	public void notificar(String firebaseToken, String titulo, String cuerpo) {
		Notification notificacion = Notification.builder().setTitle(titulo).setBody(cuerpo).build();

		Message message = Message.builder()
				.setNotification(notificacion)
				.putData("message", cuerpo)
				.setToken(firebaseToken)
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

	@Override
	public VacunadorDTO getVacunadorDTO(Vacunador vacunador) {
		VacunadorDTO vacDTO = new VacunadorDTO(vacunador.getCi(), vacunador.getEmail(), vacunador.getNombre());
		return vacDTO;
	}

	@Override
	public CiudadanoDTO getCiudadanoDTO(Ciudadano ciudadano) {
		CiudadanoDTO ciudDTO = new CiudadanoDTO(ciudadano.getCi(), ciudadano.getNombre(), ciudadano.getEmail(), false);
		return ciudDTO;
	}

	@Override
	public List<Asignacion> findAsignacionesVacunador(String cid) {
		int ci = Integer.valueOf(cid);
		return ciudadanoRepository.findAsignacionesVacunador(ci);

	}

}