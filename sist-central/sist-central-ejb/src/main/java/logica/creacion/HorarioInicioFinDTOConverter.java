package logica.creacion;

import datos.dtos.HoraInicioFinDTO;
import datos.entidades.HoraInicioFin;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HorarioInicioFinDTOConverter implements Converter<HoraInicioFinDTO, HoraInicioFin> {
    @Override
    public HoraInicioFin convert(HoraInicioFinDTO source) {
        HoraInicioFinBuilder builder = new HoraInicioFinBuilder();
        return builder.setFin(source.getFin())
                .setInicio(source.getInicio())
                .setCapasidadPorTurno(source.getCapasidadPorTurno())
                .setMinutosTurno(source.getMinutosTurno())
                .createHoraInicioFin();
    }
}
