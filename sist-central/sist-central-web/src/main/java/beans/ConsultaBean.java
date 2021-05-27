package beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import datos.entidades.Vacunatorio;

@Named("ConsultaBean")
@SessionScoped
public class ConsultaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String consultaVacunatorio;
	private String consultaPlanVacunacion;
	private static String consultaVacunatorioStatic;

	public String getConsultaVacunatorio() {
		return consultaVacunatorio;
	}

	public void setConsultaVacunatorio(String vac) {
		this.consultaVacunatorio = vac;
		this.consultaVacunatorioStatic = consultaVacunatorio;
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ConsultaVacunatorio.xhtml");
	}
	
	public static String getConsultaVacunatorioStatic() {
		return consultaVacunatorioStatic;
	}

	public String getConsultaPlanVacunacion() {
		return consultaPlanVacunacion;
	}

	public void setConsultaPlanVacunacion(String consultaPlanVacunacion) {
		this.consultaPlanVacunacion = consultaPlanVacunacion;
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ConsultaPlanVacunacion.xhtml");
	}
	
}
