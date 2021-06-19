package logica.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
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

import org.hibernate.mapping.Collection;

import datos.dtos.AgendaDTO2;
import datos.dtos.AsignacionDTO;
import datos.dtos.EtapaDTO2;
import datos.dtos.LoteDTO;
import datos.dtos.PlanVacunacionDTO2;
import datos.dtos.PuestoVacunacionDTO;
import datos.dtos.ReservaDTO;
import datos.dtos.TurnoDTO;
import datos.dtos.VacunatorioDTO;
import datos.entidades.Agenda;
import datos.entidades.Asignacion;
import datos.entidades.Ciudadano;
import datos.entidades.Departamento;
import datos.entidades.Etapa;
import datos.entidades.Intervalo;
import datos.entidades.Lote;
import datos.entidades.PlanVacunacion;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Reserva;
import datos.entidades.RestriccionEtapa;
import datos.entidades.SocioLogistico;
import datos.entidades.Turno;
import datos.entidades.Vacuna;
import datos.entidades.Vacunador;
import datos.entidades.Vacunatorio;
import datos.exceptions.CiudadanoRegistradoException;
import datos.repositorios.AgendaRepositoryLocal;
import datos.repositorios.AgendaRepositoryRemote;
import datos.repositorios.CiudadanoRepositoryRemote;
import datos.repositorios.EtapaRepositoryRemote;
import datos.repositorios.IntervaloRepositoryRemote;
import datos.repositorios.LoteRepositoryRemote;
import datos.repositorios.PlanVacunacionRepositoryRemote;
import datos.repositorios.PuestoVacunacionRepositoryRemote;
import datos.repositorios.ReservaRepositoryRemote;
import datos.repositorios.TurnoRepositoryRemote;
import datos.repositorios.VacunaRepositoryRemote;
import datos.repositorios.VacunatorioRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryRemote;


public class DatosVacunatorioSchedule extends TimerTask {
	
    private DatosVacunatorio datos;
    
    public void run() {
    	Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/rest/vacunatorios/vacunatorio/Hospital_de_Artigas"); 
		Invocation invocation = target.request().buildGet();
		Response response = invocation.invoke();		
		datos = response.readEntity(DatosVacunatorio.class);

		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		
		Context ctx;
		try {
			ctx = new InitialContext(props);
			/*String jndiName = "ejb:Vacunatorio/Vacunatorio-ejb/ControllerVacunatorio!logica.negocios.ControllerVacunatorioRemote";
			ControllerVacunatorioRemote controllerVacunatorio = (ControllerVacunatorioRemote)ctx.lookup(jndiName);
			controllerVacunatorio.ActualizarDatos(datos);*/
			String jndiName;
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/ReservaRepository!datos.repositorios.ReservaRepositoryRemote";
			ReservaRepositoryRemote reservaRepository= (ReservaRepositoryRemote)ctx.lookup(jndiName);
			reservaRepository.drop();
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/IntervaloRepository!datos.repositorios.IntervaloRepositoryRemote";
			IntervaloRepositoryRemote intervaloRepository = (IntervaloRepositoryRemote)ctx.lookup(jndiName);
			intervaloRepository.drop();
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/AgendaRepository!datos.repositorios.AgendaRepositoryRemote";
			AgendaRepositoryRemote agendaRepository= (AgendaRepositoryRemote)ctx.lookup(jndiName);
			agendaRepository.drop();
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/EtapaRepository!datos.repositorios.EtapaRepositoryRemote";
			EtapaRepositoryRemote etapaRepository= (EtapaRepositoryRemote)ctx.lookup(jndiName);
			etapaRepository.drop();
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/PlanVacunacionRepository!datos.repositorios.PlanVacunacionRepositoryRemote";
			PlanVacunacionRepositoryRemote planVacunacionRepository = (PlanVacunacionRepositoryRemote)ctx.lookup(jndiName);
			planVacunacionRepository.drop();
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/VacunatorioRepository!datos.repositorios.VacunatorioRepositoryRemote";
			VacunatorioRepositoryRemote vacRepository = (VacunatorioRepositoryRemote)ctx.lookup(jndiName);
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/PuestoVacunacionRepository!datos.repositorios.PuestoVacunacionRepositoryRemote";
			PuestoVacunacionRepositoryRemote pVacRepository = (PuestoVacunacionRepositoryRemote)ctx.lookup(jndiName);
			pVacRepository.dropAsignaciones();
			pVacRepository.drop();
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/TurnoRepository!datos.repositorios.TurnoRepositoryRemote";
			TurnoRepositoryRemote turnoRepository = (TurnoRepositoryRemote)ctx.lookup(jndiName);
			turnoRepository.drop();
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/CiudadanoRepository!datos.repositorios.CiudadanoRepositoryRemote";
			CiudadanoRepositoryRemote ciudadanoRepository = (CiudadanoRepositoryRemote)ctx.lookup(jndiName);
			ciudadanoRepository.drop();
			vacRepository.drop();
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/LoteRepository!datos.repositorios.LoteRepositoryRemote";
			LoteRepositoryRemote loteRepository = (LoteRepositoryRemote)ctx.lookup(jndiName);
			loteRepository.drop();
			jndiName = "ejb:Vacunatorio2/Vacunatorio2-ejb/VacunaRepository!datos.repositorios.VacunaRepositoryRemote";
			VacunaRepositoryRemote vacunaRepository = (VacunaRepositoryRemote)ctx.lookup(jndiName);
			vacunaRepository.drop();
			
			VacunatorioDTO vacDTO = datos.getVac();
			Vacunatorio vacunatorio = new Vacunatorio(vacDTO.getNombre(), vacDTO.getCiudad(), vacDTO.getDireccion(), vacDTO.getDepartamento());
			vacRepository.save(vacunatorio);
			List<Vacunatorio> vacs = vacRepository.find();
			Vacunatorio vac = vacs.get(0);
			
			//asignaciones
			
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
			
			//reservas
			
			List<Reserva> reservas = new ArrayList<>();
			for(ReservaDTO reservaDTO:datos.getReservas()) {
				Reserva res = new Reserva();
				res.setCodigo(reservaDTO.getCodigo());
				res.setParaDosis(reservaDTO.getParaDosis());
				res.setEstado(reservaDTO.getEstado());
				res.setLote(reservaDTO.getLote());
				if(ciudadanoRepository.findByNombreCi(reservaDTO.getCiudadano().getCi()) == null) {
					Ciudadano ciud = new Ciudadano(reservaDTO.getCiudadano().getCi(), reservaDTO.getCiudadano().getNombre(), reservaDTO.getCiudadano().getEmail());
					try {
						ciudadanoRepository.save(ciud);
					} catch (CiudadanoRegistradoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Ciudadano ciudadano = ciudadanoRepository.findByNombreCi(reservaDTO.getCiudadano().getCi());
				res.setCiudadano(ciudadano);
				PlanVacunacionDTO2 planVacDTO = reservaDTO.getIntervalo().getAgenda2().getEtapa().getPlanVac();
				if(planVacunacionRepository.findByNombre(planVacDTO.getNombre()) == null) {
					PlanVacunacion planVac = new PlanVacunacion(planVacDTO.getNombre(), LocalDate.parse(planVacDTO.getInicio()), LocalDate.parse(planVacDTO.getFin()), planVacDTO.getEnfermedad());
					planVacunacionRepository.save(planVac);
				}
				PlanVacunacion planVacunacion = planVacunacionRepository.findByNombre(planVacDTO.getNombre());
				EtapaDTO2 etapaDTO = reservaDTO.getIntervalo().getAgenda2().getEtapa();
				if(etapaRepository.find(etapaDTO) == null) {
					Etapa etap = new Etapa(etapaDTO.getDescripcion(), LocalDate.parse(etapaDTO.getInicio()), LocalDate.parse(etapaDTO.getFin()), etapaDTO.getVacuna(), planVacunacion);
					etapaRepository.save(etap);
				}
				Etapa etapa = etapaRepository.find(etapaDTO);
				AgendaDTO2 agendaDTO = reservaDTO.getIntervalo().getAgenda2();
				Turno turno = turnoRepository.find(vac.getNombre(), agendaDTO.getTurno().getNombre()).get(0);
				if(agendaRepository.find(agendaDTO.getNombre(), etapa, agendaDTO.getTurno().getNombre()) == null) {
					//en caso de que no se haya definido una fecha de fin
					LocalDate fechaFin;
					if(agendaDTO.getFin().equals("")) {
						fechaFin = null;
					} else {
						fechaFin = LocalDate.parse(agendaDTO.getFin());
					}
					Agenda agend = new Agenda(agendaDTO.getNombre(), LocalDate.parse(agendaDTO.getInicio()), fechaFin, etapa, turno);
					agendaRepository.save(agend);
				}
				Agenda agenda = agendaRepository.find(agendaDTO.getNombre(), etapa, agendaDTO.getTurno().getNombre());
				if(intervaloRepository.findByFecha(agenda.getId(), LocalDateTime.parse(reservaDTO.getIntervalo().getFechayHora())).isEmpty()) {
					Intervalo inter = new Intervalo(LocalDateTime.parse(reservaDTO.getIntervalo().getFechayHora()), agenda);
					intervaloRepository.save(inter);
				}
				Intervalo intervalo = null;
				List<Intervalo> intervalos = intervaloRepository.find();				
				for (Intervalo interv:intervalos) {
					if (interv.getFechayHora().isEqual(LocalDateTime.parse(reservaDTO.getIntervalo().getFechayHora()))) {
						intervalo = interv;
						break;
					}
				}
				res.setIntervalo(intervalo);
				reservaRepository.save(res);
				
			}

			//lotes
			
			Set<LoteDTO> lotesDTO = vacDTO.getLotes();
			for(LoteDTO loteDTO:lotesDTO) {
				if(vacunaRepository.findByNombreVacuna(loteDTO.getVacuna().getNombre()).isEmpty()) {
					vacunaRepository.save(loteDTO.getVacuna().getNombre(), loteDTO.getVacuna().getCantDosis(), loteDTO.getVacuna().getInmunidadMeses(), loteDTO.getVacuna().getDosisSeparacionDias(), null, null);
				}
				Vacuna vacuna = vacunaRepository.findByNombreVacuna(loteDTO.getVacuna().getNombre()).get(0);
				Lote lote = new Lote(loteDTO.getDosisDisponibles(), loteDTO.getNumeroLote(), LocalDate.parse(loteDTO.getFechaVencimiento()), vacuna, LocalDate.parse(loteDTO.getFechaEntrega()), LocalDate.parse(loteDTO.getFechaDespacho()));
				loteRepository.save(lote);
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
