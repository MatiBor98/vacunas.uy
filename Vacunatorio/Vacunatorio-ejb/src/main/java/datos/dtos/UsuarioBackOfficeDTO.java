package datos.dtos;

import java.io.Serializable;

public class UsuarioBackOfficeDTO implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
    private String rol;

    public UsuarioBackOfficeDTO() {}

    public UsuarioBackOfficeDTO(String email, String rol) {
        this.email = email;
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRol() {
    	return rol;
    }
    
    public void setRol(String rol) {
    	this.rol = rol;
    }

    @Override
    public String toString() {
        return email + "" + rol;
    }
}
