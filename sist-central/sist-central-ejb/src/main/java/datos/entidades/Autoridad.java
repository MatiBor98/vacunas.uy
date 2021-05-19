package datos.entidades;

import datos.entidades.UsuarioBO;
import java.io.Serializable;
import javax.persistence.*;

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
