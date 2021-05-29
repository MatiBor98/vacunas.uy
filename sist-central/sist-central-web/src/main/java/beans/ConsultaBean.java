package beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Vacunatorio;
import datos.exceptions.CiudadanoNoEncontradoException;

@Named("ConsultaBean")
@SessionScoped
public class ConsultaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String consultaVacunatorio;
	private int consultaUsuario;
	private String consultaUsuarioNombre;
	private String consultaUsuarioEmail;
	private boolean consultaUsuarioVacunador;
	private static int consultaUsuarioStatic;
	private String consultaPlanVacunacion;
	private static String consultaVacunatorioStatic;
	private static String consultaPlanVacunacionStatic;
	private String consultaSocioLogistico;
	private static String consultaSocioLogisticoStatic;
	private boolean modificando;

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
	
	public static String getConsultaPlanVacunacionStatic() {
		return consultaPlanVacunacionStatic;
	}

	public void setConsultaPlanVacunacion(String consultaPlanVacunacion) {
		this.consultaPlanVacunacion = consultaPlanVacunacion;
		this.consultaPlanVacunacionStatic = consultaPlanVacunacion;
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ConsultaPlanVacunacion.xhtml");
	}
	public String getConsultaSocioLogistico() {
		return consultaVacunatorio;
	}
	public void setConsultaSocioLogistico(String soc) {
		this.consultaSocioLogistico = soc;
		this.setConsultaSocioLogisticoStatic(consultaSocioLogistico);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ConsultaSocioLogistico.xhtml");
	}
	public static String getConsultaSocioLogisticoStatic() {
		return consultaVacunatorioStatic;
	}

	public int getConsultaUsuario() {
		return consultaUsuario;
	}

	public void setConsultaUsuario(int consultaUsuarioXD, String nombre, String email, boolean esVacunador) {
		setModificando(false);
		this.consultaUsuario = consultaUsuarioXD;
		this.consultaUsuarioStatic = consultaUsuario;
		this.consultaUsuarioEmail = email;
		this.consultaUsuarioNombre = nombre;
		this.consultaUsuarioVacunador = esVacunador;	
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ConsultaUsuarioFrontOffice.xhtml");
	}

	public boolean getModificando() {
		return modificando;
	}

	public void setModificando(boolean modificando) {
		this.modificando = modificando;
	}

	public static int getConsultaUusarioStatic() {
		return consultaUsuarioStatic;
	}

	public static void setConsultaUusarioStatic(int consultaUusarioStatic) {
		ConsultaBean.consultaUsuarioStatic = consultaUusarioStatic;
	}

	public static void setConsultaSocioLogisticoStatic(String consultaSocioLogisticoStatic) {
		ConsultaBean.consultaSocioLogisticoStatic = consultaSocioLogisticoStatic;
	}

	public String getConsultaUsuarioNombre() {
		return consultaUsuarioNombre;
	}

	public void setConsultaUsuarioNombre(String consultaUsuarioNombre) {
		this.consultaUsuarioNombre = consultaUsuarioNombre;
	}

	public String getConsultaUsuarioEmail() {
		return consultaUsuarioEmail;
	}

	public void setConsultaUsuarioEmail(String consultaUsuarioEmail) {
		this.consultaUsuarioEmail = consultaUsuarioEmail;
	}

	public boolean getConsultaUsuarioVacunador() {
		return consultaUsuarioVacunador;
	}

	public void setConsultaUsuarioVacunador(boolean consultaUsuarioVacunador) {
		this.consultaUsuarioVacunador = consultaUsuarioVacunador;
	}
	
}
