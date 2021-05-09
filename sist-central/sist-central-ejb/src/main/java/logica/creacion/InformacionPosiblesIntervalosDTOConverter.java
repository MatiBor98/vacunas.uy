package logica.creacion;

import datos.dtos.HoraInicioFinDTO;
import datos.entidades.InformacionPosiblesIntervalos;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InformacionPosiblesIntervalosDTOConverter implements Converter<HoraInicioFinDTO, InformacionPosiblesIntervalos> {
    @Override
    public InformacionPosiblesIntervalos convert(HoraInicioFinDTO source) {
        InformacionPosiblesIntervalosBuilder builder = new InformacionPosiblesIntervalosBuilder();
        return builder.setFin(source.getFin())
                .setInicio(source.getInicio())
                .setCapasidadPorTurno(source.getCapasidadPorTurno())
                .setMinutosTurno(source.getMinutosTurno())
                .createHoraInicioFin();
    }
}
