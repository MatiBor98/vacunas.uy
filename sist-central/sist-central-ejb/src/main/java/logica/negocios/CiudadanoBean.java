package logica.negocios;

import datos.dtos.AgendaDTO;
import datos.dtos.CiudadanoDTO;
import datos.entidades.Agenda;
import datos.entidades.Asignacion;
import datos.entidades.Ciudadano;
import datos.entidades.Vacunador;
import datos.exceptions.CiudadanoNoEncontradoException;
import datos.repositorios.CiudadanoRepositoryLocal;
import logica.creacion.Converter;
import logica.servicios.local.CiudadanoServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.time.LocalDate;
import java.util.HashMap;
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

    @Override
    public void save(CiudadanoDTO ciudadanoDTO) {
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
    	if(userNew.getEmail() != userLegacy.getEmail() || userNew.getNombre() != userLegacy.getNombre()) {
    		ciudadanoRepository.refreshCiudadano(userNew);
    	}
    }
}