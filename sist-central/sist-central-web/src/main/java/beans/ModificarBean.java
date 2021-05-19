package beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("ModificarBean")
@SessionScoped
public class ModificarBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String modificarVacuna;

	public String getModificarVacuna() {
		return modificarVacuna;
	}

	public void setModificarVacuna(String modificarVacuna) {
		this.modificarVacuna = modificarVacuna;
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ModificarVacuna.xhtml");
	}
	
	
	
}
