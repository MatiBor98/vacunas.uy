package laboratorio.tse;

import java.time.LocalDate;
import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb3.annotation.ResourceAdapter;

import laboratorio.tse.entidades.Lote;
import laboratorio.tse.repositorios.LoteRepositoryLocal;


@ResourceAdapter("oas-connection")
@MessageDriven(
        name = "sistCentralTopic",
        activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
            @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/sist-central"),
            @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
        })
public class TopicoMDB implements MessageListener {
	@EJB
	LoteRepositoryLocal service;
	
	public TopicoMDB() {
	}

	
	public void onMessage(Message message) {
		TextMessage msg = null;
		if (message instanceof TextMessage) {
			msg = (TextMessage) message;
			try {
				String[] partes= msg.getText().split("\\|");
				Lote lote = new Lote(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]),partes[2], LocalDate.parse(partes[3]), partes[4]);
				service.save(lote);
				//informarSocLog(21, 21, "21", LocalDate.now(), "21", "21");
			} catch (JMSException e) {
				e.printStackTrace();
			//} catch (NamingException e) {
			//	// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
		}
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
		String destinationString = System.getProperty("destination", "queue/socio-logistico");
		Destination destination = (Destination) namingContext.lookup(destinationString);
		String content = System.getProperty("message.content", dosisDisponibles + "|" + numeroLote + "|" + nomVac + "|" + fechaVencimiento + "|" + vacunaNombre + "|" + socioLogisticoNombre + "|");
		JMSContext context = connectionFactory.createContext(userName, password);
		context.createProducer().send(destination, content);

	}
	
}
