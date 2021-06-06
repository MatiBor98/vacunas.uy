package logica.creacion;

import datos.dtos.PlanVacunacionDTO;
import datos.entidades.PlanVacunacion;

import java.time.ZoneId;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlanVacunacionConverter implements Converter<PlanVacunacion, PlanVacunacionDTO> {
    @Override
    public PlanVacunacionDTO convert(PlanVacunacion source) {
        PlanVacunacionDTOBuilder builder = new PlanVacunacionDTOBuilder();
        return builder.setInicio(source.getInicio())
                .setFin(source.getFin())
                .setNombre(source.getNombre())
                .setEnfermedad(null)
                .createPlanVacunacionDTO();
    }
}
