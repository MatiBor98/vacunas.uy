package logica.creacion;

import datos.dtos.InformacionPosiblesIntervalosDTO;
import datos.entidades.InformacionPosiblesIntervalos;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InformacionPosiblesIntervalosConverter implements Converter<InformacionPosiblesIntervalos, InformacionPosiblesIntervalosDTO> {
    @Override
    public InformacionPosiblesIntervalosDTO convert(InformacionPosiblesIntervalos source) {
        InformacionPosiblesIntervalosDTOBuilder builder = new InformacionPosiblesIntervalosDTOBuilder();
        return builder.setFin(source.getFin())
                .setInicio(source.getInicio())
                .setCapasidadPorTurno(source.getCapasidadPorTurno())
                .setMinutosTurno(source.getMinutosTurno())
                .createHoraInicioFinDTO();
    }
}
