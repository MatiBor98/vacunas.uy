package logica.creacion;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Ciudadano;
import datos.entidades.Vacunador;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CiudadanoConverter implements Converter<Ciudadano, CiudadanoDTO> {

    @Override
    public CiudadanoDTO convert(Ciudadano ciudadano) {
        CiudadanoDTOBuilder builder = new CiudadanoDTOBuilder();
        boolean vacunador = false; 
        if(ciudadano instanceof Vacunador) {
        	vacunador = true;
        }
        return builder.setCi(ciudadano.getCi()).setNombre(ciudadano.getNombre()).setEmail(ciudadano.getEmail()).setVacunador(vacunador).createCiudadanoDTO();
        
    }
}