package logica.creacion;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Ciudadano;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CiudadanoDTOConverter implements Converter<CiudadanoDTO, Ciudadano> {

    @Override
    public Ciudadano convert(CiudadanoDTO ciudadanoDTO) {
        CiudadanoBuilder builder = new CiudadanoBuilder();


        return builder.setCi(ciudadanoDTO.getCi()).setNombre(ciudadanoDTO.getNombre()).setEmail(ciudadanoDTO.getEmail()).createCiudadano();
    }
}