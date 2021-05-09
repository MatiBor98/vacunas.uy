package logica.creacion;

import datos.dtos.HoraInicioFinDTO;
import datos.entidades.InformacionPosiblesIntervalos;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InformacionPosiblesIntervalosConverter implements Converter<InformacionPosiblesIntervalos, HoraInicioFinDTO> {
    @Override
    public HoraInicioFinDTO convert(InformacionPosiblesIntervalos source) {
        InformacionPosiblesIntervalosDTOBuilder builder = new InformacionPosiblesIntervalosDTOBuilder();
        return builder.setFin(source.getFin())
                .setInicio(source.getInicio())
                .setCapasidadPorTurno(source.getCapasidadPorTurno())
                .setMinutosTurno(source.getMinutosTurno())
                .createHoraInicioFinDTO();
    }
}
