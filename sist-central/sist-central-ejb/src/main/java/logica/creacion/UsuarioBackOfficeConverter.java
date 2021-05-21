package logica.creacion;

import datos.dtos.UsuarioBackOfficeDTO;
import datos.entidades.Administrador;
import datos.entidades.UsuarioBO;

public class UsuarioBackOfficeConverter implements Converter<UsuarioBO, UsuarioBackOfficeDTO> {

	@Override
	public UsuarioBackOfficeDTO convert(UsuarioBO source) {
		UsuarioBackOfficeDTOBuilder builder = new UsuarioBackOfficeDTOBuilder();
		String rol;
		if(source instanceof Administrador) {
			rol = "administrador";
		}
		else {
			rol = "autoridad";
		}
		return builder.setEmail(source.getEmail()).setRol(rol).createUsuarioBackOfficeDTO();
		
	}

}
