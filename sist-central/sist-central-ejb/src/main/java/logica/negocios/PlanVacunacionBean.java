package logica.negocios;

import datos.dtos.PlanVacunacionDTO;
import datos.entidades.PlanVacunacion;
import datos.repositorios.PlanVacunacionRepository;
import logica.creacion.Converter;
import logica.servicios.local.PlanVacunacionServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PlanVacunacionBean implements PlanVacunacionServiceLocal {

    @Inject
    private Converter<PlanVacunacionDTO, PlanVacunacion> planVacunacionDTOPlanVacunacionConverter;

    @Inject
    private Converter<PlanVacunacion, PlanVacunacionDTO> planVacunacionPlanVacunacionDTOConverter;

    @EJB
    private PlanVacunacionRepository planVacunacionRepository;

    @Override
    public PlanVacunacionDTO save(PlanVacunacionDTO planVacunacionDTO) {
        PlanVacunacion planVacunacion = planVacunacionDTOPlanVacunacionConverter.convert(planVacunacionDTO);
        planVacunacionRepository.save(planVacunacion);
        return planVacunacionPlanVacunacionDTOConverter.convert(planVacunacion);
    }
}
