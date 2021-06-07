package mdb;


import logica.servicios.local.LoteServiceLocal;

import javax.ejb.ActivationConfigProperty;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.time.LocalDate;


@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/socio-logistico"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
        })
public class SocioLogistQueueMDB implements MessageListener {

    @EJB
    LoteServiceLocal loteService;

    public SocioLogistQueueMDB() {
    }

    public void onMessage(Message message) {
        TextMessage msg = null;
        if (message instanceof TextMessage) {
            msg = (TextMessage) message;
            try {
                String texto = msg.getText();
                System.out.println(texto);
                String[] partes= texto.split("\\|");
                switch(partes[3]) {
                    case "Despachado":
                        loteService.despacharLote(Integer.parseInt(partes[0]), partes[1], LocalDate.parse(partes[2]));
                        break;
                    case "Entregado":
                        loteService.entregarLote(Integer.parseInt(partes[0]), partes[1], LocalDate.parse(partes[2]));

                        break;
                    default:
                        System.out.println("no entro");
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
