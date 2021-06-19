package mdb;

import logica.servicios.local.ReservaServiceLocal;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/vacunatorio"),
				@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
		})
public class QueueMDB implements MessageListener {
	
	@EJB
    private ReservaServiceLocal reservaService;

    public QueueMDB() {
    }
	
    public void onMessage(Message message) {
        TextMessage msg = null;
        if (message instanceof TextMessage) {
        	msg = (TextMessage) message;
        	try {
				String texto = msg.getText();
				List<String> reservasFinalizadas = Pattern.compile("\\&").splitAsStream(texto).collect(Collectors.toList());
				if(!reservasFinalizadas.get(0).equals("Confirmadas:")) {
					String reservasConf = reservasFinalizadas.get(0);
					reservasConf = reservasConf.replace("Confirmadas:", "");
					List<String> reservasConfirmadas = Pattern.compile("\\|").splitAsStream(reservasConf).collect(Collectors.toList());
					for(String reserva:reservasConfirmadas) {
						List<String> reservaYLote = Pattern.compile("\\,").splitAsStream(reserva).collect(Collectors.toList());
						reservaService.confirmarVacuna(Integer.parseInt(reservaYLote.get(0)), reservaYLote.get(1));
					}
				}
				if(!reservasFinalizadas.get(1).equals("Caducadas:")) {
					String reservasCanc = reservasFinalizadas.get(1);
					reservasCanc = reservasCanc.replace("Caducadas:", "");
					List<String> reservasCanceladas = Pattern.compile("\\|").splitAsStream(reservasCanc).collect(Collectors.toList());
					for(String reserva:reservasCanceladas) {
						reservaService.cancelarVacuna(Integer.parseInt(reserva));
					}
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
        }
    }

}
