package logica.schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import datos.entidades.Estado;
import datos.entidades.Reserva;
import datos.entidades.ReservaConfirmada;
import datos.repositorios.ReservaRepositoryRemote;


public class EnviarReservasConfirmadasSchedule extends TimerTask {
    
	private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private static final String DEFAULT_DESTINATION = "jms/queue/vacunatorio";
	private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
	private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";
	
    public void run() {
    	Context namingContext = null;
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
		try {
			namingContext = new InitialContext(env);
			String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
			ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
			String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
			Destination destination = (Destination) namingContext.lookup(destinationString);
			
			
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
			props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			props.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
			
			Context ctx;
			ctx = new InitialContext(props);
			String jndiName;
			jndiName = "ejb:Vacunatorio/Vacunatorio-ejb/ReservaRepository!datos.repositorios.ReservaRepositoryRemote";
			ReservaRepositoryRemote reservaRepository= (ReservaRepositoryRemote)ctx.lookup(jndiName);
			
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
			try (JMSContext context = connectionFactory.createContext()){
				context.createProducer().send(destination, res);
				for(ReservaConfirmada resConf:reservasConfPendientes) {
					//le cambio el estado para que no se manden siempre todas
					resConf.setEstado("enviado");
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}

    }
}
