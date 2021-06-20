
package plataformainteroperabilidad;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sexo.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sexo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Hombre"/&gt;
 *     &lt;enumeration value="Mujer"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "sexo")
@XmlEnum
public enum Sexo {

    @XmlEnumValue("Hombre")
    HOMBRE("Hombre"),
    @XmlEnumValue("Mujer")
    MUJER("Mujer");
    private final String value;

    Sexo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Sexo fromValue(String v) {
        for (Sexo c: Sexo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
