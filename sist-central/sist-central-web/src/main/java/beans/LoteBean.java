package beans;

import logica.servicios.local.LoteServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Properties;

@Named("LoteBean")
@RequestScoped
public class LoteBean implements Serializable{

	private static final long serialVersionUID = 1L;


	private String vacunatorio;
	private Integer numLote;
	private String socLogistico;
	private String vacuna;
	private Integer dosisDisponibles;
	private LocalDate fechaVencimiento;
	private String loteYaExiste = "none";
	private String loteAgregado = "none";
	private String loteEliminado = "none";
	private String loteNoEliminado = "none";
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	
	@EJB
	logica.servicios.local.LoteServiceLocal loteService;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public void agregarLote(String vac) throws NamingException {
		//falta hacer el control de que ya este ingresado
		loteService.addLote(dosisDisponibles, numLote,vac,fechaVencimiento, vacuna,socLogistico);
		informarSocLog(dosisDisponibles, numLote,vac,fechaVencimiento, vacuna,socLogistico);
		this.setLoteYaExiste("none");
		this.setLoteAgregado("block");
		this.setVacunatorio(null);
		this.setNumLote(null);
		this.setSocLogistico(null);
		this.setVacuna(null);
		this.setDosisDisponibles(null);
		this.setFechaVencimiento(null);


	}

	public String getColor() {
		if (this.color.equals("white")) {
			this.color = "#222938";
			this.colorSecundario = "white";
		} else {
			this.color = "white";
			this.colorSecundario = "#222938";
		}
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColorSecundario() {
		return colorSecundario;
	}
	public void setColorSecundario(String colorSecundario) {
		this.colorSecundario = colorSecundario;
	}
	/*public String hayVacunas() {
		String res;
		List<Vacuna> vacs = (List<Vacuna>) vacService.find();
		if (vacs.isEmpty()) {
			res = "block";
			
		} else {
			res = "none";
		}
		return res;
	}*/
	public String getBusqueda() {
		return busqueda;
	}
	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}
	public Boolean getRealizarBusqueda() {
		return realizarBusqueda;
	}
	public void setRealizarBusqueda(Boolean realizarBusqueda) {
		this.realizarBusqueda = realizarBusqueda;
		if ((this.busqueda != null) && (!this.busqueda.equals(""))) {
			this.hayBusqueda = "block";
		} else {
			this.hayBusqueda = "none";
		}
	}
	public String getHayBusqueda() {
		return hayBusqueda;
	}
	public void setHayBusqueda(String hayBusqueda) {
		this.hayBusqueda = hayBusqueda;
	}


	public String getLoteYaExiste() {
		return loteYaExiste;
	}

	public void setLoteYaExiste(String loteYaExiste) {
		this.loteYaExiste = loteYaExiste;
	}

	public String getVacunatorio() {
		return vacunatorio;
	}

	public void setVacunatorio(String vacunatorio) {
		this.vacunatorio = vacunatorio;
	}

	public void setNumLote(Integer numLote) {
		this.numLote = numLote;
	}

	public void setSocLogistico(String socLogistico) {
		this.socLogistico = socLogistico;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public Integer getNumLote() {
		return numLote;
	}

	public String getSocLogistico() {
		return socLogistico;
	}

	public String getVacuna() {
		return vacuna;
	}

	public void setVacuna(String vacuna) {
		this.vacuna = vacuna;
	}

	public Integer getDosisDisponibles() {
		return dosisDisponibles;
	}

	public void setDosisDisponibles(Integer dosisDisponibles) {
		this.dosisDisponibles = dosisDisponibles;
	}

	public String getLoteAgregado() {
		return loteAgregado;
	}

	public void setLoteAgregado(String loteAgregado) {
		this.loteAgregado = loteAgregado;
	}

	public String getLoteEliminado() {
		return loteEliminado;
	}

	public void setLoteEliminado(String loteEliminado) {
		this.loteEliminado = loteEliminado;
	}

	public String getLoteNoEliminado() {
		return loteNoEliminado;
	}

	public void setLoteNoEliminado(String loteNoEliminado) {
		this.loteNoEliminado = loteNoEliminado;
	}

	public LoteServiceLocal getLoteService() {
		return loteService;
	}

	public void setLoteService(LoteServiceLocal loteService) {
		this.loteService = loteService;
	}

	public void informarSocLog(int dosisDisponibles, int numeroLote, String nomVac, LocalDate fechaVencimiento, String vacunaNombre, String socioLogisticoNombre) throws NamingException {
		String userName = "alta1";
		String password = "alta1";
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080"));
		env.put(Context.SECURITY_PRINCIPAL, userName);
		env.put(Context.SECURITY_CREDENTIALS, password);
		Context namingContext = null;
		namingContext = new InitialContext(env);
		String connectionFactoryString = System.getProperty("connection.factory", "jms/RemoteConnectionFactory");
		ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
		String destinationString = System.getProperty("destination", "topic/sist-central");
		Destination destination = (Destination) namingContext.lookup(destinationString);
		String content = System.getProperty("message.content", dosisDisponibles + "|" + numeroLote + "|" + nomVac + "|" + fechaVencimiento + "|" + vacunaNombre);
		JMSContext context = connectionFactory.createContext(userName, password);
		context.createProducer().send(destination, content);

	}
}
