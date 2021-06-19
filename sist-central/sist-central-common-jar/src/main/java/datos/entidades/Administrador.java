package datos.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: Administrador
 *
 */
@Entity
@DiscriminatorValue("administrador")
public class Administrador extends UsuarioBO implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Administrador() {
		super();
	}
   
}
