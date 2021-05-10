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
public class CiudadanoDTOConverter implements Converter<CiudadanoDTO, Ciudadano> {

    @Override
    public Ciudadano convert(CiudadanoDTO ciudadanoDTO) {
        CiudadanoBuilder builder = new CiudadanoBuilder();


        return builder.setCi(ciudadanoDTO.getCi()).setNombre(ciudadanoDTO.getNombre()).setEmail(ciudadanoDTO.getEmail()).createCiudadano();
    }
}