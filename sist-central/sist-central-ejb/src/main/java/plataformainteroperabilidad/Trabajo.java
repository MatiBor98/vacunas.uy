
package plataformainteroperabilidad;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para trabajo.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="trabajo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Desempleado"/&gt;
 *     &lt;enumeration value="Agricultura"/&gt;
 *     &lt;enumeration value="Alimentacion"/&gt;
 *     &lt;enumeration value="Comercio"/&gt;
 *     &lt;enumeration value="Construccion"/&gt;
 *     &lt;enumeration value="Educacion"/&gt;
 *     &lt;enumeration value="Fabricacion"/&gt;
 *     &lt;enumeration value="Publico"/&gt;
 *     &lt;enumeration value="Hoteleria"/&gt;
 *     &lt;enumeration value="Quimica"/&gt;
 *     &lt;enumeration value="Comunicacion"/&gt;
 *     &lt;enumeration value="Servicios"/&gt;
 *     &lt;enumeration value="Salud"/&gt;
 *     &lt;enumeration value="Finanzas"/&gt;
 *     &lt;enumeration value="Textil"/&gt;
 *     &lt;enumeration value="Transporte"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "trabajo")
@XmlEnum
public enum Trabajo {

    @XmlEnumValue("Desempleado")
    DESEMPLEADO("Desempleado"),
    @XmlEnumValue("Agricultura")
    AGRICULTURA("Agricultura"),
    @XmlEnumValue("Alimentacion")
    ALIMENTACION("Alimentacion"),
    @XmlEnumValue("Comercio")
    COMERCIO("Comercio"),
    @XmlEnumValue("Construccion")
    CONSTRUCCION("Construccion"),
    @XmlEnumValue("Educacion")
    EDUCACION("Educacion"),
    @XmlEnumValue("Fabricacion")
    FABRICACION("Fabricacion"),
    @XmlEnumValue("Publico")
    PUBLICO("Publico"),
    @XmlEnumValue("Hoteleria")
    HOTELERIA("Hoteleria"),
    @XmlEnumValue("Quimica")
    QUIMICA("Quimica"),
    @XmlEnumValue("Comunicacion")
    COMUNICACION("Comunicacion"),
    @XmlEnumValue("Servicios")
    SERVICIOS("Servicios"),
    @XmlEnumValue("Salud")
    SALUD("Salud"),
    @XmlEnumValue("Finanzas")
    FINANZAS("Finanzas"),
    @XmlEnumValue("Textil")
    TEXTIL("Textil"),
    @XmlEnumValue("Transporte")
    TRANSPORTE("Transporte");
    private final String value;

    Trabajo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Trabajo fromValue(String v) {
        for (Trabajo c: Trabajo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
