
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/socio-logistico";
    private static final String DEFAULT_USERNAME = "alta1";
    private static final String DEFAULT_PASSWORD = "alta1";
    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";

    public static void main(String[] args) throws NamingException {
        Client client = ClientBuilder.newClient();

        WebTarget target = client.target("http://localhost:8080/rest/socioLogistico/sociologistico/hola");

        Invocation invocation = target.request().buildGet();
        Response response = invocation.invoke();

        String result = response.readEntity(new GenericType<String>() {});
        Context namingContext = null;

        try {
            String userName = System.getProperty("username", DEFAULT_USERNAME);
            String password = System.getProperty("password", DEFAULT_PASSWORD);

            // Set up the namingContext for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            env.put(Context.SECURITY_PRINCIPAL, userName);
            env.put(Context.SECURITY_CREDENTIALS, password);
            namingContext = new InitialContext(env);

            // Perform the JNDI lookups
            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
            log.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
            log.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

            String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
            log.info("Attempting to acquire destination \"" + destinationString + "\"");
            Destination destination = (Destination) namingContext.lookup(destinationString);
            log.info("Found destination \"" + destinationString + "\" in JNDI");

            int i = 1;
            Scanner sc = new Scanner(System.in);
            while (i != 0) {
                System.out.println("##########################################");
                System.out.println("Seleccione una opcion y pulse enter");
                System.out.println("1- Agregar un Ciudadano");
                System.out.println("2- Buscar un Ciudadano");
                System.out.println("3- Listar un Ciudadano");
                System.out.println("4- Salir");
                System.out.println("##########################################");

                i = sc.nextInt();
                if (i == 1) {
                    System.out.println("Ingrese una cedula");
                    String cedula = sc.next();
                    System.out.println("Ingrese un nombre");
                    String nombre = sc.next();
                    System.out.println("Ingrese un email");
                    String email = sc.next();

                    String content = System.getProperty("message.content", cedula + "|" +nombre + "|"+ email+ "|");
                    try (JMSContext context = connectionFactory.createContext(userName, password)) {
//			            try (JMSContext context = connectionFactory.createContext()) {
                        log.info("Sending messages with content: " + content);
                        // Send the specified number of messages
                        context.createProducer().send(destination, content);

                        // Create the JMS consumer
                        //JMSConsumer consumer = context.createConsumer(destination);
                        // Then receive the same number of messages that were sent
                        //String text = consumer.receiveBody(String.class, 5000);
                        //log.info("Received message with content " + text);
                    }

                } else if (i == 2) {

                } else if (i == 3) {

                } else if (i == 4) {
                    i = 0;

                } else {
                    System.out.println("Opcion invalida");
                    System.out.println("");

                }

            }
        } catch (NamingException e) {
            log.severe(e.getMessage());
        } finally {
            if (namingContext != null) {
                try {
                    namingContext.close();
                } catch (NamingException e) {
                    log.severe(e.getMessage());
                }
            }
        }

    }

}
