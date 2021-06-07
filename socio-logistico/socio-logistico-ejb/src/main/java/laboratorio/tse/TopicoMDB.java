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
			} catch (JMSException e) {
				e.printStackTrace();
			//} catch (NamingException e) {
			//	// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
		}
	}
	
	

	
}
