package logica.creacion;

import datos.dtos.UsuarioBackOfficeDTO;

public class UsuarioBackOfficeDTOBuilder {
	
	String email;
	String rol;

	public UsuarioBackOfficeDTOBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public UsuarioBackOfficeDTOBuilder setRol(String rol) {
		this.rol = rol;
		return this;
	}
	
	public UsuarioBackOfficeDTO createUsuarioBackOfficeDTO() {
		return new UsuarioBackOfficeDTO(email, rol);
	}
}
