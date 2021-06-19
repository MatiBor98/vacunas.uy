package Main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;





public class Main {
	
	private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private static final String DEFAULT_DESTINATION = "jms/queue/vacunatorio";
	private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
	private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";
	

	public static void main(String[] args) throws IOException, NamingException {
		
		Context namingContext = null;
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
		namingContext = new InitialContext(env);
		
		String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
		ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
		String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
		Destination destination = (Destination) namingContext.lookup(destinationString);
		
		namingContext = new InitialContext(env);
		BufferedReader reader = new BufferedReader(
		new InputStreamReader(System.in));
		Scanner in = new Scanner(System.in);
		int eleccion = 0;

		while (eleccion != 3) {
			
			System.out.println("Bienvenido, seleccione una opcion");
			System.out.println("1. Mandar mensaje a la cola");
			System.out.println("2. Salir");
			System.out.println("");
			eleccion = in.nextInt();
			System.out.println("");
			
			
			
		
			if(eleccion == 1) {
				int eleccionCase1 = 0;
				String message;
				System.out.println("Escriba su mensaje");
				System.out.println("");
				message = reader.readLine();
				System.out.println("");
				System.out.println("Desea confirmar el mensaje?");
				System.out.println(message);
				System.out.println("");
				System.out.println("1. Confirmar");
				System.out.println("2. Cancelar");
				System.out.println("");
				eleccionCase1 = in.nextInt();
				if (eleccionCase1 == 1) {
					try (JMSContext context = connectionFactory.createContext()){
						context.createProducer().send(destination, message);
					}
				} else if (eleccionCase1 == 2) {
					System.out.println("Se descarto el mensaje");
					System.out.println("");
				}

			} else if (eleccion != 2){
				System.out.println("Seleccion invalida.");
				System.out.println("");
			} else {
				System.out.println("Adios.");
				System.out.println("");
			}
		}
	}

}
