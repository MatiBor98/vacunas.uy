package logica.schedule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import datos.dtos.AsignacionDTO;
import datos.dtos.PuestoVacunacionDTO;
import datos.dtos.TurnoDTO;
import datos.dtos.VacunatorioDTO;
import datos.entidades.Asignacion;
import datos.entidades.Departamento;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Turno;
import datos.entidades.Vacunador;
import datos.entidades.Vacunatorio;
import datos.exceptions.CiudadanoRegistradoException;
import datos.repositorios.AgendaRepositoryLocal;
import datos.repositorios.CiudadanoRepositoryRemote;
import datos.repositorios.PuestoVacunacionRepositoryRemote;
import datos.repositorios.TurnoRepositoryRemote;
import datos.repositorios.VacunatorioRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryRemote;


public class DatosVacunatorioSchedule extends TimerTask {
	
    private DatosVacunatorio datos;
    
    public void run() {
    	Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/rest/vacunatorios/vacunatorio/COSEM Punta Carretas"); 
		Invocation invocation = target.request().buildGet();
		Response response = invocation.invoke();		
		datos = response.readEntity(DatosVacunatorio.class);

		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		props.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
		
		Context ctx;
		try {
			ctx = new InitialContext(props);
			/*String jndiName = "ejb:Vacunatorio/Vacunatorio-ejb/ControllerVacunatorio!logica.negocios.ControllerVacunatorioRemote";
			ControllerVacunatorioRemote controllerVacunatorio = (ControllerVacunatorioRemote)ctx.lookup(jndiName);
			controllerVacunatorio.ActualizarDatos(datos);*/
			String jndiName = "ejb:Vacunatorio/Vacunatorio-ejb/VacunatorioRepository!datos.repositorios.VacunatorioRepositoryRemote";
			VacunatorioRepositoryRemote vacRepository = (VacunatorioRepositoryRemote)ctx.lookup(jndiName);
			jndiName = "ejb:Vacunatorio/Vacunatorio-ejb/PuestoVacunacionRepository!datos.repositorios.PuestoVacunacionRepositoryRemote";
			PuestoVacunacionRepositoryRemote pVacRepository = (PuestoVacunacionRepositoryRemote)ctx.lookup(jndiName);
			pVacRepository.dropAsignaciones();
			pVacRepository.drop();
			jndiName = "ejb:Vacunatorio/Vacunatorio-ejb/TurnoRepository!datos.repositorios.TurnoRepositoryRemote";
			TurnoRepositoryRemote turnoRepository = (TurnoRepositoryRemote)ctx.lookup(jndiName);
			turnoRepository.drop();
			jndiName = "ejb:Vacunatorio/Vacunatorio-ejb/CiudadanoRepository!datos.repositorios.CiudadanoRepositoryRemote";
			CiudadanoRepositoryRemote ciudadanoRepository = (CiudadanoRepositoryRemote)ctx.lookup(jndiName);
			ciudadanoRepository.drop();
			vacRepository.drop();
			
			VacunatorioDTO vacDTO = datos.getVac();
			Vacunatorio vac = new Vacunatorio(vacDTO.getNombre(), vacDTO.getCiudad(), vacDTO.getDireccion(), vacDTO.getDepartamento());
			vacRepository.save(vac);
			for(TurnoDTO turnoDTO:vacDTO.getTurnos()) {
				Turno turno = new Turno(turnoDTO.getNombre(), LocalTime.parse(turnoDTO.getInicio()), LocalTime.parse(turnoDTO.getFin()), vac);
				turnoRepository.save(turno);
			}
			List<PuestoVacunacion> pVacs = new ArrayList<>();
			List<Vacunador> vacunadores = new ArrayList<>();
			for(PuestoVacunacionDTO pVacDTO:vacDTO.getPuestosVacunacion()) {
				PuestoVacunacion pVac = new PuestoVacunacion(vac, pVacDTO.getNombrePuesto());
				pVacRepository.save(pVac);
				List<Asignacion> asigs =  new ArrayList<>();
				for(AsignacionDTO asigDTO:pVacDTO.getAsignaciones()) {
					Turno turnoAsignado = turnoRepository.find(vac.getNombre(), asigDTO.getTurno().getNombre()).get(0);
					Vacunador vacunador = null;
					if(!existeVacunador(asigDTO.getVacunador().getCi(), vacunadores)) {				
						vacunador = new Vacunador(asigDTO.getVacunador().getCi(), asigDTO.getVacunador().getEmail(), asigDTO.getVacunador().getNombre());
						try {
							ciudadanoRepository.save(vacunador);
						} catch (CiudadanoRegistradoException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						vacunadores.add(vacunador);
					} 
					Vacunador vacAsig = (Vacunador) ciudadanoRepository.findByNombreCi(asigDTO.getVacunador().getCi());
					Turno turnoAsig = turnoRepository.find(vac.getNombre(), asigDTO.getTurno().getNombre()).get(0);
					PuestoVacunacion pVacAsig = pVacRepository.find(pVacDTO.getNomVacunatorio(), pVacDTO.getNombrePuesto()).get(0);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
					Asignacion asig = new Asignacion(vacAsig, turnoAsig, pVacAsig, LocalDate.parse(asigDTO.getFechaInicio(), formatter), LocalDate.parse(asigDTO.getFechaFin(), formatter));
					pVacRepository.addAsignacion(asig);
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
    	
    }
    Boolean existeVacunador(int ci, List<Vacunador> vacs) {
    	Boolean res = false;
    	for(Vacunador vac:vacs) {
    		if(vac.getCi() == ci) {
    			res = true;
    			break;
    		}
    	}
    	return res;
    }
}
