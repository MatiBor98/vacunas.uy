package beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import datos.dtos.CiudadanoDTO;
import datos.dtos.UsuarioBackOfficeDTO;
import datos.entidades.Ciudadano;
import logica.servicios.local.CiudadanoServiceLocal;

@Named
@SessionScoped
public class UsuarioFrontOfficeBean implements Serializable {

	@EJB
	CiudadanoServiceLocal usuarios;
	
	public UsuarioFrontOfficeBean() {
		// TODO Auto-generated constructor stub
	}
	
	public List<CiudadanoDTO> listarUsuarios(){
		return usuarios.find();
	}

}
