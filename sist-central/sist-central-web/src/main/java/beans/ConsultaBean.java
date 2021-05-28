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
	private static String consultaVacunatorioStatic;
	private String consultaSocioLogistico;
	private static String consultaSocioLogisticoStatic;

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

	public String getConsultaSocioLogistico() {
		return consultaVacunatorio;
	}
	public void setConsultaSocioLogistico(String soc) {
		this.consultaSocioLogistico = soc;
		this.consultaSocioLogisticoStatic = consultaSocioLogistico;
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ConsultaSocioLogistico.xhtml");
	}
	public static String getConsultaSocioLogisticoStatic() {
		return consultaVacunatorioStatic;
	}


}
