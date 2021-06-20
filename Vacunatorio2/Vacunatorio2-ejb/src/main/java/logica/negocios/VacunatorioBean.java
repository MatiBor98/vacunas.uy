package logica.negocios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import datos.dtos.AgendaDTO2;
import datos.dtos.AsignacionDTO;
import datos.dtos.EtapaDTO2;
import datos.dtos.LoteDTO;
import datos.dtos.PlanVacunacionDTO2;
import datos.dtos.PuestoVacunacionDTO;
import datos.dtos.ReservaDTO;
import datos.dtos.TurnoDTO;
import datos.dtos.VacunatorioDTO;
import datos.entidades.*;
import datos.exceptions.CiudadanoRegistradoException;
import datos.exceptions.PuestoVacunacionNoExistenteException;
import datos.exceptions.VacunatorioNoExistenteException;
import datos.repositorios.AgendaRepositoryLocal;
import datos.repositorios.AgendaRepositoryRemote;
import datos.repositorios.CiudadanoRepositoryRemote;
import datos.repositorios.EtapaRepositoryRemote;
import datos.repositorios.IntervaloRepositoryRemote;
import datos.repositorios.LoteRepositoryLocal;
import datos.repositorios.LoteRepositoryRemote;
import datos.repositorios.PlanVacunacionRepositoryRemote;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
import datos.repositorios.PuestoVacunacionRepositoryRemote;
import datos.repositorios.ReservaRepositoryLocal;
import datos.repositorios.ReservaRepositoryRemote;
import datos.repositorios.TurnoRepositoryLocal;
import datos.repositorios.TurnoRepositoryRemote;
import datos.repositorios.VacunaRepositoryRemote;
import datos.repositorios.VacunatorioRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryRemote;
import logica.inicio.StartupBean;
import logica.schedule.DatosVacunatorio;
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
	private ReservaRepositoryLocal reservaRepositoryLocal;
	
	@EJB
	private LoteRepositoryLocal loteRepositoryLocal;
	
	@EJB
	private StartupBean startUpBean;
	
	
	
    public VacunatorioBean() {
    }

    public void addVacunatorio(String nombre, String ciudad, String direccion, Departamento departamento) {
    	Vacunatorio vac = new Vacunatorio(nombre, ciudad, direccion, departamento);
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

	public void addPuestoAlVacunatorio(String nombreVacunatorio, int idPuesto) {
		Vacunatorio vac = vacunatorioRepositoryLocal.find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
		PuestoVacunacion puesto = puestoVacunacionRepositoryLocal.find(idPuesto).orElseThrow(PuestoVacunacionNoExistenteException::new);
		vac.getPuestosVacunacion().add(puesto);
	}

	public void addLoteAlVacunatorio(String nombreVacunatorio, int idPuesto) {
		Vacunatorio vac = vacunatorioRepositoryLocal.find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
		PuestoVacunacion puesto = puestoVacunacionRepositoryLocal.find(idPuesto).orElseThrow(PuestoVacunacionNoExistenteException::new);
		vac.getPuestosVacunacion().add(puesto);
	}

	public List<Vacunatorio> findByPage(int primerResultado, int limiteResultados) {
		return vacunatorioRepositoryLocal.find(primerResultado, limiteResultados);
		
	}
	
	
	
	@Override
	public int addTurno(String nombreTurno, LocalTime inicio, LocalTime fin, String nombreVacunatorio) {
		Vacunatorio vacunatorio = vacunatorioRepositoryLocal.find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
		Turno turno = new Turno(nombreTurno, inicio, fin, vacunatorio);
		turnoRepositoryLocal.save(turno);
		return turno.getId();
	}

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

	@Override
	public List<Reserva> findReservas() {
		List<Reserva> res = new ArrayList<>();
		List<Reserva> reservas = reservaRepositoryLocal.find();
		if (!reservas.isEmpty()) {
			res = reservas;
		}
		return res;
	}

	@Override
	public ReservaConfirmada findReservaConfirmada(int codigo) {	
		return reservaRepositoryLocal.findReservaConfirmada(codigo);
	}

	@Override
	public void saveReservaConfirmada(ReservaConfirmada resConf) {
		reservaRepositoryLocal.saveReservaConfrimada(resConf);
		
	}

	@Override
	public List<Lote> findLotes() {		
		return loteRepositoryLocal.find();
	}

	@Override
	public void actualizar() {
		startUpBean.run();
		/*DatosVacunatorioSchedule dvs = new DatosVacunatorioSchedule();
		dvs.run();*/
		//DatosVacunatorioSchedule schMain = new DatosVacunatorioSchedule();
		//schMain.run();
	}

}
