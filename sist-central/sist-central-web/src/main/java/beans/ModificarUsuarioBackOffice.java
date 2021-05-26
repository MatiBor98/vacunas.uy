package beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class ModificarUsuarioBackOffice implements Serializable {

	private static final long serialVersionUID = 1L;

	private String modificarUsuario;

	
	public String getModificarUsuario() {
		return modificarUsuario;
	}

	public void setModificarVacuna(String modificarUsuario) {
		this.modificarUsuario = modificarUsuario;
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ModificarUsuarioBackOffice.xhtml");
	}

}
