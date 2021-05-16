package logica.creacion;

import datos.dtos.EtapaDTO;
import datos.entidades.Etapa;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EtapaCoverter implements Converter<Etapa, EtapaDTO> {

    @Override
    public EtapaDTO convert(Etapa source) {
        EtapaDTOBuilder builder = new EtapaDTOBuilder();
        return builder.setVacuna(source.getVacuna().getNombre())
                .setPlanVacunacion(source.getPlanVacunacion().getNombre())
                .setInicio(source.getInicio())
                .setFin(source.getFin())
                .setDescripcion(source.getDescripcion())
                .setId(source.getId())
                .createEtapaDTO();
    }
}