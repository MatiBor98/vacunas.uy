
package plataformainteroperabilidad;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ciudadanosService", targetNamespace = "http://webservices.samples.jboss.org/", wsdlLocation = "file:/C:/Users/User/git/laboratorio-tse-2021/sist-central/sist-central-ejb/src/main/resources/ciudadanos.wsdl")
public class CiudadanosService
    extends Service
{

    private final static URL CIUDADANOSSERVICE_WSDL_LOCATION;
    private final static WebServiceException CIUDADANOSSERVICE_EXCEPTION;
    private final static QName CIUDADANOSSERVICE_QNAME = new QName("http://webservices.samples.jboss.org/", "ciudadanosService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Users/User/git/laboratorio-tse-2021/sist-central/sist-central-ejb/src/main/resources/ciudadanos.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CIUDADANOSSERVICE_WSDL_LOCATION = url;
        CIUDADANOSSERVICE_EXCEPTION = e;
    }

    public CiudadanosService() {
        super(__getWsdlLocation(), CIUDADANOSSERVICE_QNAME);
    }

    public CiudadanosService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CIUDADANOSSERVICE_QNAME, features);
    }

    public CiudadanosService(URL wsdlLocation) {
        super(wsdlLocation, CIUDADANOSSERVICE_QNAME);
    }

    public CiudadanosService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CIUDADANOSSERVICE_QNAME, features);
    }

    public CiudadanosService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CiudadanosService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Ciudadanos
     */
    @WebEndpoint(name = "ciudadanosPort")
    public Ciudadanos getCiudadanosPort() {
        return super.getPort(new QName("http://webservices.samples.jboss.org/", "ciudadanosPort"), Ciudadanos.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Ciudadanos
     */
    @WebEndpoint(name = "ciudadanosPort")
    public Ciudadanos getCiudadanosPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://webservices.samples.jboss.org/", "ciudadanosPort"), Ciudadanos.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CIUDADANOSSERVICE_EXCEPTION!= null) {
            throw CIUDADANOSSERVICE_EXCEPTION;
        }
        return CIUDADANOSSERVICE_WSDL_LOCATION;
    }

}
