package datos.entidades;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TipoDeUsuario")
public abstract class UsuarioBO {

	@Id
	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}
	
	public UsuarioBO() {
		super();
	}

	public UsuarioBO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
