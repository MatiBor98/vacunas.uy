package laboratorio.tse;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.ejb3.annotation.ResourceAdapter;


@ResourceAdapter("oas-connection")
@MessageDriven(
        name = "sistCentralTopic",
        activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
            @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/sist-central"),
            @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
        })
public class TopicoMDB implements MessageListener {
	public TopicoMDB() {
	}

	public void onMessage(Message message) {
		TextMessage msg = null;
		if (message instanceof TextMessage) {
			msg = (TextMessage) message;
			try {
				String texto = msg.getText();
				System.out.println(texto);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
