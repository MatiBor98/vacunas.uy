package datos.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: Autoridad
 *
 */
@Entity
@DiscriminatorValue("autoridad")
public class Autoridad extends UsuarioBO implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Autoridad() {
		super();
	}
   
}
