package logica.creacion;

import datos.dtos.HoraInicioFinDTO;
import datos.entidades.HoraInicioFin;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HorarioInicioFinConverter implements Converter<HoraInicioFin, HoraInicioFinDTO> {
    @Override
    public HoraInicioFinDTO convert(HoraInicioFin source) {
        HoraInicioFinDTOBuilder builder = new HoraInicioFinDTOBuilder();
        return builder.setFin(source.getFin())
                .setInicio(source.getInicio())
                .setCapasidadPorTurno(source.getCapasidadPorTurno())
                .setMinutosTurno(source.getMinutosTurno())
                .createHoraInicioFinDTO();
    }
}
