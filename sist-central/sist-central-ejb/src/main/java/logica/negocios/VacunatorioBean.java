package logica.negocios;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.vividsolutions.jts.geom.Point;

import datos.dtos.AsignacionDTO;
import datos.dtos.LoteDTO;
import datos.dtos.PuestoVacunacionDTO;
import datos.dtos.ReservaDTO;
import datos.dtos.TurnoDTO;
import datos.dtos.VacunatorioDTO;
import datos.entidades.*;
import datos.exceptions.PuestoVacunacionNoExistenteException;
import datos.exceptions.VacunatorioNoExistenteException;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
import datos.repositorios.TurnoRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryLocal;
import logica.creacion.VacunatorioToDto;
import logica.schedule.DatosVacunatorio;
import logica.servicios.local.LoteServiceLocal;
import logica.servicios.local.PuestoVacunacionBeanLocal;
import logica.servicios.local.ReservaServiceLocal;
import logica.servicios.local.SocioLogisticoControllerLocal;
import logica.servicios.local.TurnoServiceLocal;
import logica.servicios.local.VacunatorioControllerLocal;


@Stateless
@LocalBean
public class VacunatorioBean implements  VacunatorioControllerLocal {

	@EJB
	private VacunatorioRepositoryLocal vacunatorioRepositoryLocal;
	
	@EJB
	private PuestoVacunacionRepositoryLocal puestoVacunacionRepositoryLocal;
	
	@EJB
	private TurnoRepositoryLocal turnoRepositoryLocal;
	
	@EJB
	private PuestoVacunacionBeanLocal pVacBean;
	
	@EJB
	private TurnoServiceLocal turnoBean;
	
	@EJB
	private ReservaServiceLocal reservaBean;
	
	@EJB
	private LoteServiceLocal loteBean;
	
	
    public VacunatorioBean() {
    }

    public void addVacunatorio(String nombre, String ciudad, String direccion, Departamento departamento, Point ubicacion) {
    	Vacunatorio vac = new Vacunatorio(nombre, ciudad, direccion, departamento);
    	vac.setUbicacion(ubicacion);
    	vacunatorioRepositoryLocal.save(vac);
    }
    
    public Optional<Vacunatorio> find(String nombre) {
    	return vacunatorioRepositoryLocal.find(nombre);

    }
    
	public Optional<Vacunatorio> findWithEverything(String nombre) {
    	return vacunatorioRepositoryLocal.findWithEverything(nombre);

	}
	
	
    public List<Vacunatorio> find() {
    	return vacunatorioRepositoryLocal.find();
    }


	/*public void addLoteAlVacunatorio(String nombreVacunatorio, int idPuesto) {
		Vacunatorio vac = vacunatorioRepositoryLocal.find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
		PuestoVacunacion puesto = puestoVacunacionRepositoryLocal.find(idPuesto).orElseThrow(PuestoVacunacionNoExistenteException::new);
		vac.getPuestosVacunacion().add(puesto);
	}*/

	public List<Vacunatorio> findByPage(int primerResultado, int limiteResultados) {
		return vacunatorioRepositoryLocal.find(primerResultado, limiteResultados);
		
	}
	
	
	/*@Override
	public int addTurno(String nombreTurno, LocalTime inicio, LocalTime fin, String nombreVacunatorio) {
		Vacunatorio vacunatorio = vacunatorioRepositoryLocal.find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
		Turno turno = new Turno(nombreTurno, inicio, fin, vacunatorio);
		turnoRepositoryLocal.save(turno);
		return turno.getId();
	}*/

	public List<Vacunatorio> findByDepartamento(Departamento dep, int primerResultado, int maximosResultados) {
		return vacunatorioRepositoryLocal.findByDepartamento(dep, primerResultado, maximosResultados);
	}
	
	public List<Vacunatorio> findByDepartamento(Departamento dep) {
		return vacunatorioRepositoryLocal.findByDepartamento(dep);
	}
	
	
	public List<String> getNombresDepartamentos() {
		List<String> res = new ArrayList<>();
		Departamento[] deps = Departamento.values();
		for(Departamento dep:deps) {
			res.add(dep.toString());
		}
		return res;
	}

	public DatosVacunatorio getDatosVacunatorio(String nombreVacunatorio) {
		Date now = new Date();
		Vacunatorio vac = find(nombreVacunatorio).get();
		VacunatorioDTO vacDTO = new VacunatorioDTO(nombreVacunatorio, vac.getCiudad(), vac.getDireccion(), vac.getDepartamento());
		//asignaciones
		List<PuestoVacunacionDTO> pVacs = pVacBean.getDTO(vac);
		vacDTO.setPuestosVacunacion(pVacs);
		List<TurnoDTO> turnosDTO = new ArrayList<>();
		for(Turno turno:vac.getTurnos()) {
			TurnoDTO turnoDTO = turnoBean.getTurnoDTO(turno);
			turnosDTO.add(turnoDTO);
		}
		vacDTO.setTurnos(turnosDTO);
		//reservas
		List<ReservaDTO> reservas = reservaBean.getReservasDTO(vac);
		//lotes
		Set<Lote> lotes = vac.getLotes();
		Set<LoteDTO> lotesDTO = new HashSet<> ();
		for(Lote lote:lotes) {
			//si es null el lote aun no fue entregado
			if(lote.getFechaEntrega() != null) {
				LoteDTO loteDTO = loteBean.getLoteDTO(lote);
				lotesDTO.add(loteDTO);
			}
		}
		vacDTO.setLotes(lotesDTO);
		DatosVacunatorio datos = new DatosVacunatorio(now, vacDTO, reservas);
		return datos;
		
	}
	
	public List<Vacunatorio> getVacunatoriosCercanos(Double coordX, Double coordY){
		return vacunatorioRepositoryLocal.findVacunatorioCercano(coordX, coordY);
	}
}
