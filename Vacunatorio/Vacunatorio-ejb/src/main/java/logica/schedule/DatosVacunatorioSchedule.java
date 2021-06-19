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
import datos.repositorios.CiudadanoRepositoryLocal;
import datos.repositorios.CiudadanoRepositoryRemote;
import datos.repositorios.EtapaRepositoryLocal;
import datos.repositorios.EtapaRepositoryRemote;
import datos.repositorios.IntervaloRepositoryLocal;
import datos.repositorios.IntervaloRepositoryRemote;
import datos.repositorios.LoteRepositoryLocal;
import datos.repositorios.LoteRepositoryRemote;
import datos.repositorios.PlanVacunacionRepositoryLocal;
import datos.repositorios.PlanVacunacionRepositoryRemote;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
import datos.repositorios.PuestoVacunacionRepositoryRemote;
import datos.repositorios.ReservaRepositoryLocal;
import datos.repositorios.ReservaRepositoryRemote;
import datos.repositorios.TurnoRepositoryLocal;
import datos.repositorios.TurnoRepositoryRemote;
import datos.repositorios.VacunaRepositoryLocal;
import datos.repositorios.VacunaRepositoryRemote;
import datos.repositorios.VacunatorioRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryRemote;


public class DatosVacunatorioSchedule {
	
	@EJB
    private AgendaRepositoryLocal agendaRepository;
	@EJB
    private CiudadanoRepositoryLocal ciudadanoRepository;
	@EJB
    private IntervaloRepositoryLocal intervaloRepository;
	@EJB
    private ReservaRepositoryLocal reservaRepository;
	@EJB
    private EtapaRepositoryLocal etapaRepository;
	@EJB
    private PlanVacunacionRepositoryLocal planVacunacionRepository;
	@EJB
    private VacunatorioRepositoryLocal vacRepository;
	@EJB
    private PuestoVacunacionRepositoryLocal pVacRepository;
	@EJB
    private TurnoRepositoryLocal turnoRepository;
	@EJB
    private LoteRepositoryLocal loteRepository;
	@EJB
    private VacunaRepositoryLocal vacunaRepository;
	
    private DatosVacunatorio datos;
    
    public void run() {
    	Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://vacunas.web.elasticloud.uy/rest/vacunatorios/vacunatorio/COSEM_Punta_Carretas"); 
		Invocation invocation = target.request().buildGet();
		Response response = invocation.invoke();		
		datos = response.readEntity(DatosVacunatorio.class);

		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		props.put(Context.PROVIDER_URL, "http-remoting://vacunas.web.elasticloud.uy/rest/vacunatorios/vacunatorio/COSEM_Punta_Carretas:80");
		
		intervaloRepository.drop();
		agendaRepository.drop();
		etapaRepository.drop();
		planVacunacionRepository.drop();
		pVacRepository.dropAsignaciones();
		pVacRepository.drop();
		turnoRepository.drop();
		ciudadanoRepository.drop();
		vacRepository.drop();
		loteRepository.drop();
		vacunaRepository.drop();
		reservaRepository.drop();

		
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
