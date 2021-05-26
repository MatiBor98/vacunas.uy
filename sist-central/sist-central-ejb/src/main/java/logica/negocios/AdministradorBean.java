package logica.negocios;

import java.time.LocalDate;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import datos.entidades.Administrador;
import datos.entidades.Asignacion;
import datos.entidades.Autoridad;
import datos.entidades.Ciudadano;
import datos.entidades.UsuarioBO;
import datos.entidades.Vacunador;
import datos.exceptions.EmailRegistradoException;
import datos.repositorios.CiudadanoRepositoryLocal;
import datos.repositorios.UsuariosBackOfficeRepositoryLocal;
import logica.servicios.filter.BackOfficeInterceptor;
import logica.servicios.local.AdministradorServiceLocal;

/**
 * Session Bean implementation class AdministradorBean
 */
//@Interceptors({BackOfficeInterceptor.class})
@Stateless
@LocalBean
public class AdministradorBean implements AdministradorServiceLocal {

	@EJB
	UsuariosBackOfficeRepositoryLocal usuariosBO;
	
	@EJB
	CiudadanoRepositoryLocal usuariosFO;
    /**
     * Default constructor. 
     */
    public AdministradorBean() {
        // TODO Auto-generated constructor stub
    }

    public void addBOUser(String jwt, String email, String password, String rol) throws EmailRegistradoException {
    	UsuarioBO nuevoUsuario;
    	if(rol.equals("administrador")) {
    		nuevoUsuario = new Administrador();
    	}
    	else{
    		nuevoUsuario = new Autoridad();
    	}
    	nuevoUsuario.setEmail(email);
    	nuevoUsuario.setPassword(password);
    	usuariosBO.save(nuevoUsuario);
    }
    
    
    
    
}
