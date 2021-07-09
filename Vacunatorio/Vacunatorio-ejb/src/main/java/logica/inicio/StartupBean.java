package logica.inicio;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.eclipse.microprofile.config.Config;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.net.ssl.SSLContext;

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
import datos.entidades.Estado;
import datos.entidades.Etapa;
import datos.entidades.Intervalo;
import datos.entidades.Lote;
import datos.entidades.PlanVacunacion;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Reserva;
import datos.entidades.ReservaConfirmada;
import datos.entidades.Turno;
import datos.entidades.Vacuna;
import datos.entidades.Vacunador;
import datos.entidades.Vacunatorio;
import datos.exceptions.CiudadanoRegistradoException;
import datos.repositorios.AgendaRepositoryLocal;
import datos.repositorios.CiudadanoRepositoryLocal;
import datos.repositorios.EtapaRepositoryLocal;
import datos.repositorios.IntervaloRepositoryLocal;
import datos.repositorios.LoteRepositoryLocal;
import datos.repositorios.PlanVacunacionRepositoryLocal;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
import datos.repositorios.ReservaRepositoryLocal;
import datos.repositorios.ReservaRepositoryRemote;
import datos.repositorios.TurnoRepositoryLocal;
import datos.repositorios.VacunaRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryLocal;
import logica.schedule.DatosVacunatorio;
import android.util.Base64;

@Singleton
@Lock(LockType.WRITE)
@Startup
@LocalBean
public class StartupBean {
	
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
	@Inject
	Config config;
	

	
    private DatosVacunatorio datos;
	
	@Schedule(hour = "*", minute = "*", second = "0")
	void DatosVacunatorioSchedule() throws InterruptedException{
		run();
	}
	
	@Schedule(hour = "*", minute = "*", second = "30")
	void EnviarReservasConfirmadasSchedule() throws InterruptedException{
		run2();
	}
	
	public void run() {
		
		
    	/*Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://vacunas.web.elasticloud.uy/rest/vacunatorios/vacunatorio/"+config.getValue("nombre", String.class)); 
=======
    	Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://vacunas.web.elasticloud.uy/rest/vacunatorios/vacunatorio/"+config.getValue("nombre", String.class));
>>>>>>> branch 'master' of https://gitlab.fing.edu.uy/nicolas.san.martin/laboratorio-tse-2021.git
		Invocation invocation = target.request().buildGet();
		Response response = invocation.invoke();
		datos = response.readEntity(DatosVacunatorio.class);*/

		/*KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		     keyPairGen.initialize(2048);
		     KeyPair pair = keyPairGen.generateKeyPair();   
		     PublicKey publicKey = pair.getPublic();
		     byte[] publicKeyByte = publicKey.getEncoded();
		     String publicKeyString = Base64.encodeToString(publicKeyByte, Base64.NO_WRAP); 
		     PrivateKey privateKey = pair.getPrivate();
		     byte[] privateKeyByte = privateKey.getEncoded();
		     String privateKeyString = Base64.encodeToString(privateKeyByte, Base64.NO_WRAP);*/
		try {
			
			datos = enviarPorHTTPS();
			
			reservaRepository.drop();
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
					if (interv.getFechayHora().isEqual(LocalDateTime.parse(reservaDTO.getIntervalo().getFechayHora())) && interv.getAgenda().getId() == agenda.getId()) {
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
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
    
    public void run2() {

		String userName = "alta1";
		String password = "alta1";
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, "http-remoting://vacunas.web.elasticloud.uy:80"));
		env.put(Context.SECURITY_PRINCIPAL, userName);
		env.put(Context.SECURITY_CREDENTIALS, password);
		try {
			Context namingContext = null;
			namingContext = new InitialContext(env);
			String connectionFactoryString = System.getProperty("connection.factory", "jms/RemoteConnectionFactory");
			ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
			String destinationString = System.getProperty("destination", "jms/queue/vacunatorio");
			Destination destination = (Destination) namingContext.lookup(destinationString);
			
			List<ReservaConfirmada> reservasConf = reservaRepository.findReservasConfirmadas();
			List<ReservaConfirmada> reservasConfPendientes = new ArrayList<>(); 
			for(ReservaConfirmada resConf:reservasConf) {
				if(resConf.getEstado().equals("pendiente")) {
					reservasConfPendientes.add(resConf);
				}
			}
			String reservasConfirmadas = "Confirmadas:";
			for(ReservaConfirmada reservaConf:reservasConfPendientes) {
				if(reservasConfirmadas.equals("Confirmadas:")) {
					reservasConfirmadas = reservasConfirmadas + String.valueOf(reservaConf.getCodigo()) + "," + reservaConf.getLote();	
				} else {
					reservasConfirmadas = reservasConfirmadas + "|" + String.valueOf(reservaConf.getCodigo()) + "," + reservaConf.getLote();
				}
			}
			List<Reserva> reservas = reservaRepository.find();
			String reservasCaducadas = "Caducadas:";
			LocalDateTime now = LocalDateTime.now();
			for(Reserva reserva:reservas) {
				//si esta pendiente, ya paso de fecha y no fue confirmada
				if(reserva.getEstado().equals(Estado.PENDIENTE) && reserva.getIntervalo().getFechayHora().isBefore(now) && reservaRepository.findReservaConfirmada(reserva.getCodigo()) == null) {
					if(reservasCaducadas.equals("Caducadas:")) {
						reservasCaducadas = reservasCaducadas + String.valueOf(reserva.getCodigo());	
					} else {
						reservasCaducadas = reservasCaducadas + "|" + String.valueOf(reserva.getCodigo());
					}
				}
			}
			String res = reservasConfirmadas + "&" + reservasCaducadas;
			
			//encriptado
			byte[] text = encriptar(res);
			JMSContext context = connectionFactory.createContext(userName, password);
			context.createProducer().send(destination, text);
			for(ReservaConfirmada resConf:reservasConfPendientes) {
				//le cambio el estado para que no se manden siempre todas
				resConf.setEstado("enviado");
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | NamingException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
    }
    
    public DatosVacunatorio enviarPorHTTPS() throws GeneralSecurityException {
		String urlOverHttps = "https://node1531-vacunas.web.elasticloud.uy/rest/vacunatorios/vacunatorio/"+config.getValue("nombre", String.class)+"/"+config.getValue("pass", String.class);
		TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
		BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(
				socketFactoryRegistry);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
				.setConnectionManager(connectionManager).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		ResponseEntity<DatosVacunatorio> response = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, null,
				DatosVacunatorio.class);
		return response.getBody();
	}
    
    public byte[] encriptar(String datos) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); //accepts a String variable representing the required transformation and returns a Cipher object that implements the given transformation.
		String key = config.getValue("key", String.class);
    	byte[] keyByte = Base64.decode(key, Base64.NO_WRAP);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = (PublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(keyByte));
		cipher.init(Cipher.ENCRYPT_MODE, publicKey); //accepts two parameters an integer parameter representing the operation mode (encrypt/decrypt) and, a Key object representing the public key.
		cipher.update(datos.getBytes()); //accepts a byte array representing the data to be encrypted and updates the current object with the data given.
		byte[] cipherText = cipher.doFinal(); //completes the encryption operation
		return cipherText;

    }
	
}