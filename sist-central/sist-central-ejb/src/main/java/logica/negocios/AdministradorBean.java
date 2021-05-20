package logica.negocios;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import datos.entidades.Administrador;
import datos.entidades.Autoridad;
import datos.entidades.UsuarioBO;
import datos.exceptions.EmailRegistradoException;
import datos.repositorios.UsuariosBackOfficeRepositoryLocal;
import logica.servicios.filter.BackOfficeInterceptor;

/**
 * Session Bean implementation class AdministradorBean
 */
@Interceptors({BackOfficeInterceptor.class})
@Stateless
@LocalBean
public class AdministradorBean implements AdministradorBeanLocal {

	@EJB
	UsuariosBackOfficeRepositoryLocal usuarios;
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
    	usuarios.save(nuevoUsuario);
    }
    
    public void addVacunatorio() {
    	
    }
    
}
