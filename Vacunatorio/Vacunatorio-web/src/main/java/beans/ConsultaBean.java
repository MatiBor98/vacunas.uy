package beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.config.Config;

import datos.entidades.Reserva;

@Named("ConsultaBean")
@SessionScoped
public class ConsultaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Reserva consultaReserva;
	private static int consultaReservaCodigo;
	
	@EJB
	logica.servicios.local.VacunatorioControllerLocal ContVacunatorio;
	
	@Inject
    private Config config;

	public Reserva getConsultaReserva() {
		return consultaReserva;
	}

	public void setConsultaReserva(Reserva consultaReserva) {
		this.consultaReserva = consultaReserva;
		this.setConsultaReservaCodigo(consultaReserva.getCodigo());
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ConfirmarReserva.xhtml");
	}

	public static int getConsultaReservaCodigo() {
		return consultaReservaCodigo;
	}

	public void setConsultaReservaCodigo(int consultaReservaCodigo) {
		ConsultaBean.consultaReservaCodigo = consultaReservaCodigo;
	}
	
	public void actualizar() {
		ContVacunatorio.actualizar();
	}
	
	public String getNombreVacunatorio() {
		return config.getValue("nombre", String.class);
	}
	
	public String getCiudadVacunatorio() {
		return config.getValue("ciudad", String.class);
	}
	
	public String getDepartamentoVacunatorio() {
		return config.getValue("departamento", String.class);
	}
	
	public String getDireccionVacunatorio() {
		return config.getValue("direccion", String.class);
	}
	
	
	
	
}
