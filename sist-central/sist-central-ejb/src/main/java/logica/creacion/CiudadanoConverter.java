package logica.creacion;

import datos.dtos.AgendaDTO;
import datos.dtos.CiudadanoDTO;
import datos.dtos.InformacionPosiblesIntervalosDTO;
import datos.entidades.Agenda;
import datos.entidades.Ciudadano;
import datos.entidades.InformacionPosiblesIntervalos;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class CiudadanoConverter implements Converter<Ciudadano, CiudadanoDTO> {

    @Override
    public CiudadanoDTO convert(Ciudadano ciudadano) {
        CiudadanoDTOBuilder builder = new CiudadanoDTOBuilder();


        return builder.setCi(ciudadano.getCi()).setNombre(ciudadano.getNombre()).setEmail(ciudadano.getEmail()).createCiudadanoDTO();
    }
}