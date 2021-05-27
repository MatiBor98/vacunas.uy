package mdb;


import javax.ejb.ActivationConfigProperty;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/socio-logistico"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
        })
public class SocioLogistQueueMDB implements MessageListener {

    public SocioLogistQueueMDB() {
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
