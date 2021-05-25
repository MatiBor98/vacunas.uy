package logica.negocios;

import datos.dtos.PlanVacunacionDTO;
import datos.entidades.Enfermedad;
import datos.entidades.PlanVacunacion;
import datos.repositorios.PlanVacunacionRepository;
import logica.creacion.Converter;
import logica.servicios.local.PlanVacunacionServiceLocal;

import java.time.LocalDate;
import java.util.Optional;

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
    
    public void save(String nombre, LocalDate inicio, LocalDate fin, Enfermedad enfermedad) {
    	planVacunacionRepository.save(nombre, inicio, fin, enfermedad);
    }
    
    public Optional<PlanVacunacion> find(String nombre) {
    	return planVacunacionRepository.find(nombre);
    }
}
