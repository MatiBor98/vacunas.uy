package logica.creacion;

import datos.dtos.InformacionPosiblesIntervalosDTO;
import datos.entidades.InformacionPosiblesIntervalos;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InformacionPosiblesIntervalosDTOConverter implements Converter<InformacionPosiblesIntervalosDTO, InformacionPosiblesIntervalos> {
    @Override
    public InformacionPosiblesIntervalos convert(InformacionPosiblesIntervalosDTO source) {
        InformacionPosiblesIntervalosBuilder builder = new InformacionPosiblesIntervalosBuilder();
        return builder.setFin(source.getFin())
                .setInicio(source.getInicio())
                .setCapacidadPorTurno(source.getCapacidadPorTurno())
                .setMinutosTurno(source.getMinutosTurno())
                .createHoraInicioFin();
    }
}
