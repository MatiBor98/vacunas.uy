package beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("ModificarBean")
@SessionScoped
public class ModificarBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String elementoAModificar;

	public String getElementoAModificar() {
		return elementoAModificar;
	}

	public void setElementoAModificar(String elementoAModificar) {
		this.elementoAModificar = elementoAModificar;
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ModificarVacuna.xhtml");
	}
	
	
	
}
