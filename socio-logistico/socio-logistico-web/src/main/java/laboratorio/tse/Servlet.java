package laboratorio.tse;


import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/HelloWorldMDBServletClient")
public class Servlet extends HttpServlet {

    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/socio-logistico";
    private static final String DEFAULT_USERNAME = "alta1";
    private static final String DEFAULT_PASSWORD = "alta1";
    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = System.getProperty("username", DEFAULT_USERNAME);
        String password = System.getProperty("password", DEFAULT_PASSWORD);
        // Set up the namingContext for the JNDI lookup
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
        env.put(Context.SECURITY_PRINCIPAL, userName);
        env.put(Context.SECURITY_CREDENTIALS, password);
        Context namingContext = null;
        try {
            namingContext = new InitialContext(env);
            // Perform the JNDI lookups
            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
            String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
            Destination destination = (Destination) namingContext.lookup(destinationString);
            String content = System.getProperty("message.content", "cedula" + "|" + "nombre" + "|" + "email" + "|");
            try (JMSContext context = connectionFactory.createContext(userName, password)) {
                context.createProducer().send(destination, content);
            }


        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
