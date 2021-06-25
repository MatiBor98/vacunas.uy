package logica.negocios;

import datos.dtos.PlanVacunacionDTO;
import datos.dtos.PlanVacunacionDTO2;
import datos.entidades.Enfermedad;
import datos.entidades.PlanVacunacion;
import datos.repositorios.PlanVacunacionRepository;
import datos.repositorios.PlanVacunacionRepositoryLocal;
import logica.creacion.Converter;
import logica.servicios.local.PlanVacunacionServiceLocal;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
    private PlanVacunacionRepositoryLocal planVacunacionRepository;

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
    
    public List<PlanVacunacion> find() {
    	return planVacunacionRepository.find();
    }
    
    public Date getFechaFin(String nombre) {
    	Optional<PlanVacunacion> pVac = planVacunacionRepository.find(nombre);
    	LocalDate fechaFin = pVac.get().getFin();
    	Date res = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	return res;
    }

	@Override
	public PlanVacunacionDTO getPVacDTO(PlanVacunacion planVacunacion) {
		PlanVacunacionDTO res = new PlanVacunacionDTO(planVacunacion.getNombre(), planVacunacion.getInicio(), planVacunacion.getFin(), planVacunacion.getEnfermedad().getNombre());
		return res;
	}

	@Override
	public PlanVacunacionDTO2 getPVacDTO2(PlanVacunacion planVacunacion) {
		PlanVacunacionDTO2 res = new PlanVacunacionDTO2(planVacunacion.getNombre(), planVacunacion.getInicio().toString(), planVacunacion.getFin().toString(), planVacunacion.getEnfermedad().getNombre());
		return res;
	}
}

