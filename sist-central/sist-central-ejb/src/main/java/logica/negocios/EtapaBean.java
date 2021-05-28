package logica.negocios;

import datos.dtos.EtapaDTO;
import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;
import datos.entidades.Trabajos;
import datos.entidades.Vacuna;
import datos.repositorios.EtapaRepositoryLocal;
import datos.repositorios.PlanVacunacionRepositoryLocal;
import datos.repositorios.VacunaRepositoryLocal;
import logica.creacion.Converter;
import logica.servicios.local.EtapaController;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class EtapaBean  implements EtapaController {
    @EJB
    private EtapaRepositoryLocal etapaRepository;

    @EJB
    private VacunaRepositoryLocal vacunaRepository;
    
    @EJB
    private PlanVacunacionRepositoryLocal pVacRepository;
    
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
    public List<EtapaDTO> find(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos) {
        List<Etapa> etapas = etapaRepository.find(nombreEnfermedad, edadCiudadano, trabajos);
        return etapas.parallelStream().map(etapaEtapaDTOConverter::convert).collect(Collectors.toList());
    }

    @Override
    public boolean habilidadoCiudadano(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos) {
        //TODO: hacer esto en la consulta para mejorar
        return !etapaRepository.find(nombreEnfermedad, edadCiudadano, trabajos).isEmpty();
    }
    
    @Override
    public void save(String nomVac, LocalDate inicio, LocalDate fin, String planVacunacion, String descripcion, List<Trabajos> trabajos, int edadMin, int edadMax) {
    	Vacuna vac = vacunaRepository.findByNombreVacuna(nomVac).get(0);
    	Optional<PlanVacunacion> planVacOptional = pVacRepository.find(planVacunacion);
    	PlanVacunacion planVac = planVacOptional.get();
    	etapaRepository.save(vac, inicio, fin, planVac, descripcion, trabajos, edadMin, edadMax);
    }

	@Override
	public List<String> getNombresTrabajos() {
		List<String> res = new ArrayList<>();
		Trabajos[] trabajos = Trabajos.values();
		for(Trabajos trabajo:trabajos) {
			res.add(trabajo.toString());
		}
		return res;
	}
}
