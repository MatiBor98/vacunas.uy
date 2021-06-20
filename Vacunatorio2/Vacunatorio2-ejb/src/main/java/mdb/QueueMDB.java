//package mdb;
//
//import javax.ejb.ActivationConfigProperty;
//
//import javax.ejb.MessageDriven;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.MessageListener;
//import javax.jms.ObjectMessage;
//import javax.jms.TextMessage;
//
//import datos.entidades.Asignacion;
//import logica.schedule.DatosVacunatorio;
//
//
//@MessageDriven(
//		activationConfig = { 
//				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
//				@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/vacunatorio"),
//				@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
//		})
//public class QueueMDB implements MessageListener {
//
//    public QueueMDB() {
//    }
//	
//    public void onMessage(Message message) {
//        
//    }
//
//}
