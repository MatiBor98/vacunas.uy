package laboratorio.tse.negocios;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.wildfly.discovery.ServicesQueue;

import laboratorio.tse.entidades.Lote;
import laboratorio.tse.repositorios.LoteRepositoryLocal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Session Bean implementation class PuestoVacunacionBean
 */
@Stateless
public class LoteBean implements LoteServiceLocal {

	@EJB
	LoteRepositoryLocal loteRepositoryLocal;
	
	public void addLote(int dosisDisponibles, int numeroLote,String nombreVacunatorio, String nomVac, LocalDate fechaVencimiento, String vacunaNombre) {
		Lote lote = new Lote(dosisDisponibles,numeroLote,nombreVacunatorio, fechaVencimiento, nomVac);
		loteRepositoryLocal.save(lote);
	}


    public List<Lote> find() {
    	return loteRepositoryLocal.find();
    }



	/**
     * Default constructor.
     */
    public LoteBean() {
        // TODO Auto-generated constructor stub
    }


	/*@Override
	public void despacharLote(int numero, LocalDate fechaDespacho) {
		loteRepositoryLocal.despacharLote(numero, fechaDespacho);
		
	}*/
	
	@Override
	public void despacharLote(int numero,String nombreSocLog, LocalDate fechaDespacho) {
		loteRepositoryLocal.despacharLote(numero, fechaDespacho);
		try {
			informarSocLog(numero,nombreSocLog,fechaDespacho,"Despachado");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	@Override
	public void entregarLote(int numero, LocalDate fechaEntrega) {
		loteRepositoryLocal.entregarLote(numero, fechaEntrega);
		
	}

	@Asynchronous
	@Override
	public void transportarLoteAsync(int numero, String nombreSocLog) {
		try {
			//LocalDate fechaDespacho = LocalDate.now();
			//despacharLote(numero,fechaDespacho);
			//System.out.println("Despachado"); 
			//informarSocLog(numero,nombreSocLog,fechaDespacho,"Despachado");
			TimeUnit.SECONDS.sleep(5);
			LocalDate fechaEntrega = LocalDate.now();
			entregarLote(numero,fechaEntrega);
			informarSocLog(numero,nombreSocLog,fechaEntrega,"Entregado");
			System.out.println("Entregado"); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   		
	}

	public void informarSocLog(int numeroLote, String socioLogisticoNombre,LocalDate fecha, String mensaje) throws NamingException {
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
		String destinationString = System.getProperty("destination", "queue/socio-logistico");
		Destination destination = (Destination) namingContext.lookup(destinationString);
		String content = System.getProperty("message.content", numeroLote + "|" + socioLogisticoNombre + "|" + fecha + "|" +mensaje+ "|");
		JMSContext context = connectionFactory.createContext(userName, password);
		context.createProducer().send(destination, content);

	}


	@Override
	public Optional<Lote> find(int numeroLote) {
		return loteRepositoryLocal.find(numeroLote);
	}

}

