package datos.entidades;

import java.io.Serializable;
import javax.persistence.*;

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
