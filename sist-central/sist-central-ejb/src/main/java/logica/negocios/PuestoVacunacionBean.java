package logica.negocios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import datos.entidades.Asignacion;
import datos.entidades.Ciudadano;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Turno;
import datos.entidades.Vacunador;
import datos.entidades.Vacunatorio;
import datos.exceptions.VacunatorioNoExistenteException;
import datos.repositorios.CiudadanoRepositoryLocal;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryLocal;
import logica.servicios.local.PuestoVacunacionBeanLocal;

/**
 * Session Bean implementation class PuestoVacunacionBean
 */
@Stateless
@LocalBean
public class PuestoVacunacionBean implements  PuestoVacunacionBeanLocal {

	@EJB
	PuestoVacunacionRepositoryLocal puestoVacunacionRepositoryLocal;
	@EJB
	VacunatorioRepositoryLocal vacunatorioRepositoryLocal;
	@EJB
	CiudadanoRepositoryLocal ciudadanoRepositoryLocal;
	
    @Override
	public int addPuestoVacunacion(String nombrePuesto, String nombreVacunatorio) {
    	Vacunatorio vacunatorio = vacunatorioRepositoryLocal.find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
		PuestoVacunacion puesto = new PuestoVacunacion(vacunatorio, nombrePuesto);
		puestoVacunacionRepositoryLocal.save(puesto);
		return puesto.getId();
	}
    
    public Optional<PuestoVacunacion> find(int id) {
    	return puestoVacunacionRepositoryLocal.find(id);
    }
    
    public List<PuestoVacunacion> find() {
    	return puestoVacunacionRepositoryLocal.find();
    }

    public List<PuestoVacunacion> find(String vac, String nombrePuesto) {
    	return puestoVacunacionRepositoryLocal.find(vac, nombrePuesto);
    }
	/**
     * Default constructor. 
     */
    public PuestoVacunacionBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Vacunador> getVacunadoresNoAsignados(String nombreVacunatorio, String nombrePuesto) {
		List<Ciudadano> ciuds = ciudadanoRepositoryLocal.find();
		List<Asignacion> asigs = puestoVacunacionRepositoryLocal.getAsignaciones(nombreVacunatorio, nombrePuesto);
		List<Vacunador> vacs = new ArrayList<>();
		for (Ciudadano ciud:ciuds) {
			if (ciud instanceof Vacunador) {
				boolean encontrado = false;
				Vacunador vac = (Vacunador) ciud;
				for (Asignacion asig:asigs) {
					if (asig.getVacunador().getCi() == vac.getCi()) {
						encontrado = true;
						break;
					}
				}
				if (!encontrado) {
					vacs.add(vac);
				}
			}
		}
		return vacs;
	}

	@Override
	public void addAsignacion(Vacunador vac, Turno turn, PuestoVacunacion pVac, LocalDate localInicio,
			LocalDate localFin) {
		Asignacion asig = new Asignacion(vac, turn, pVac, localInicio, localFin);
		puestoVacunacionRepositoryLocal.addAsignacion(asig);
		
	}



}
