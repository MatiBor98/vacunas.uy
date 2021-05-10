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
        return ciudadanoRepository.find().stream().map(ciudadanoConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<CiudadanoDTO> findByNombreCi(long criterio){
        return ciudadanoRepository.findByNombreCi(criterio).stream().map(ciudadanoConverter::convert).collect(Collectors.toList());
    }

    public void save(CiudadanoDTO ciudadanoDTO) {
        ciudadanoRepository.save(ciudadanoDTOConverter.convert(ciudadanoDTO));
    }
}