package plataformainteroperabilidad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ciudadano complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ciudadano"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sexo" type="{http://webservices.samples.jboss.org/}sexo" minOccurs="0"/&gt;
 *         &lt;element name="trabajadorEscencial" type="{http://webservices.samples.jboss.org/}trabajo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ciudadano", propOrder = {
    "fechaNacimiento",
    "nombre",
    "sexo",
    "trabajadorEscencial"
})
public class Ciudadano {

    protected String fechaNacimiento;
    protected String nombre;
    @XmlSchemaType(name = "string")
    protected Sexo sexo;
    @XmlSchemaType(name = "string")
    protected Trabajo trabajadorEscencial;

    /**
     * Obtiene el valor de la propiedad fechaNacimiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Define el valor de la propiedad fechaNacimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaNacimiento(String value) {
        this.fechaNacimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad sexo.
     * 
     * @return
     *     possible object is
     *     {@link Sexo }
     *     
     */
    public Sexo getSexo() {
        return sexo;
    }

    /**
     * Define el valor de la propiedad sexo.
     * 
     * @param value
     *     allowed object is
     *     {@link Sexo }
     *     
     */
    public void setSexo(Sexo value) {
        this.sexo = value;
    }

    /**
     * Obtiene el valor de la propiedad trabajadorEscencial.
     * 
     * @return
     *     possible object is
     *     {@link Trabajo }
     *     
     */
    public Trabajo getTrabajadorEscencial() {
        return trabajadorEscencial;
    }

    /**
     * Define el valor de la propiedad trabajadorEscencial.
     * 
     * @param value
     *     allowed object is
     *     {@link Trabajo }
     *     
     */
    public void setTrabajadorEscencial(Trabajo value) {
        this.trabajadorEscencial = value;
    }

}
