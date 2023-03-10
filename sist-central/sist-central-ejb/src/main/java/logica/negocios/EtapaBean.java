package logica.negocios;

import datos.dtos.EtapaDTO;
import datos.dtos.EtapaDTO2;
import datos.dtos.PlanVacunacionDTO;
import datos.dtos.PlanVacunacionDTO2;
import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;
import plataformainteroperabilidad.Trabajo;
import datos.entidades.Vacuna;
import datos.repositorios.EtapaRepositoryLocal;
import datos.repositorios.PlanVacunacionRepositoryLocal;
import datos.repositorios.VacunaRepositoryLocal;
import logica.creacion.Converter;
import logica.servicios.local.EtapaController;
import logica.servicios.local.PlanVacunacionServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class EtapaBean  implements EtapaController {
    @EJB
    private EtapaRepositoryLocal etapaRepository;

    @EJB
    private VacunaRepositoryLocal vacunaRepository;
    
    @EJB
    private PlanVacunacionRepositoryLocal pVacRepository;
    
    @EJB
    private PlanVacunacionServiceLocal pVacService;
    
    @Inject
    private Converter<EtapaDTO, Etapa> etapaDTOEtapaConverter;

    @Inject
    private Converter<Etapa, EtapaDTO> etapaEtapaDTOConverter;

    public EtapaBean() {}

    public EtapaDTO save(EtapaDTO etapa) {
        Etapa nuevaEtapa = etapaDTOEtapaConverter.convert(etapa);
        etapaRepository.save(nuevaEtapa);
        return etapaEtapaDTOConverter.convert(nuevaEtapa);
    }

    @Override
    public Optional<EtapaDTO> find(int id) {
        return etapaRepository.find(id).map(etapaEtapaDTOConverter::convert);
    }

    @Override
    public List<EtapaDTO> find(String nombreEnfermedad, int edadCiudadano, Trabajo trabajos) {
        List<Etapa> etapas = etapaRepository.find(nombreEnfermedad, edadCiudadano, trabajos);
        return etapas.parallelStream().map(etapaEtapaDTOConverter::convert).collect(Collectors.toList());
    }

    @Override
    public boolean existeEtapaParaCiudadano(String nombreEnfermedad, int edadCiudadano, Trabajo trabajos) {
        return etapaRepository.existeEtapaParaCiudadano(nombreEnfermedad, edadCiudadano, trabajos);
    }
    
    @Override
    public void save(String nomVac, LocalDate inicio, LocalDate fin, String planVacunacion, String descripcion,
                     List<Trabajo> trabajos, int edadMin, int edadMax) {
    	Vacuna vac = vacunaRepository.findByNombreVacuna(nomVac).get(0);
    	Optional<PlanVacunacion> planVacOptional = pVacRepository.find(planVacunacion);
    	PlanVacunacion planVac = planVacOptional.get();
    	etapaRepository.save(vac, inicio, fin, planVac, descripcion, trabajos, edadMin, edadMax);
    }

    @Override
	public List<String> getNombresTrabajos() {
		return Stream.of(Trabajo.values()).map(Trabajo::toString).collect(Collectors.toList());
	}

	@Override
	public EtapaDTO getEtapaDTO(Etapa etapa) {
		PlanVacunacionDTO pVacDTO = pVacService.getPVacDTO(etapa.getPlanVacunacion());
		EtapaDTO res = new EtapaDTO(etapa.getVacuna().getNombre(), etapa.getInicio(), etapa.getFin(), pVacDTO, etapa.getDescripcion());
		return res;
	}

	@Override
	public EtapaDTO2 getEtapaDTO2(Etapa etapa) {
		PlanVacunacionDTO2 pVacDTO = pVacService.getPVacDTO2(etapa.getPlanVacunacion());
		EtapaDTO2 res = new EtapaDTO2(etapa.getVacuna().getNombre(), etapa.getInicio().toString(), etapa.getFin().toString(), pVacDTO, etapa.getDescripcion());
		return res;
	}
}
