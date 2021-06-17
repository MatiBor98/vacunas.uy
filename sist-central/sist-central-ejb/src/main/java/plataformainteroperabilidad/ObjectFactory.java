
package plataformainteroperabilidad;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the plataformainteroperabilidad package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Ciudadano_QNAME = new QName("http://webservices.samples.jboss.org/", "ciudadano");
    private final static QName _ObtPersonaPorDoc_QNAME = new QName("http://webservices.samples.jboss.org/", "obtPersonaPorDoc");
    private final static QName _ObtPersonaPorDocResponse_QNAME = new QName("http://webservices.samples.jboss.org/", "obtPersonaPorDocResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: plataformainteroperabilidad
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Ciudadano }
     * 
     */
    public Ciudadano createCiudadano() {
        return new Ciudadano();
    }

    /**
     * Create an instance of {@link ObtPersonaPorDoc }
     * 
     */
    public ObtPersonaPorDoc createObtPersonaPorDoc() {
        return new ObtPersonaPorDoc();
    }

    /**
     * Create an instance of {@link ObtPersonaPorDocResponse }
     * 
     */
    public ObtPersonaPorDocResponse createObtPersonaPorDocResponse() {
        return new ObtPersonaPorDocResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ciudadano }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Ciudadano }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.samples.jboss.org/", name = "ciudadano")
    public JAXBElement<Ciudadano> createCiudadano(Ciudadano value) {
        return new JAXBElement<Ciudadano>(_Ciudadano_QNAME, Ciudadano.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtPersonaPorDoc }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtPersonaPorDoc }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.samples.jboss.org/", name = "obtPersonaPorDoc")
    public JAXBElement<ObtPersonaPorDoc> createObtPersonaPorDoc(ObtPersonaPorDoc value) {
        return new JAXBElement<ObtPersonaPorDoc>(_ObtPersonaPorDoc_QNAME, ObtPersonaPorDoc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtPersonaPorDocResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtPersonaPorDocResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.samples.jboss.org/", name = "obtPersonaPorDocResponse")
    public JAXBElement<ObtPersonaPorDocResponse> createObtPersonaPorDocResponse(ObtPersonaPorDocResponse value) {
        return new JAXBElement<ObtPersonaPorDocResponse>(_ObtPersonaPorDocResponse_QNAME, ObtPersonaPorDocResponse.class, null, value);
    }

}
