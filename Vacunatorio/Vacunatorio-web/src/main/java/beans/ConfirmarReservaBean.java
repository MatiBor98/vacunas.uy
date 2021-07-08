package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import datos.entidades.Reserva;
import datos.entidades.ReservaConfirmada;

@Named("ConfirmarReservaBean")
@RequestScoped
public class ConfirmarReservaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String elegirLote = "none";
	private String reservaConfirmada = "none";
	private String reservaYaConfirmada = "none";
	private String lote = "";
	
	@EJB
	logica.servicios.local.VacunatorioControllerLocal ContVacunatorio;
	
	
	public void confirmar() {
		int codigo = ConsultaBean.getConsultaReservaCodigo();
		if (lote == null || lote.equals("")) {
			this.setElegirLote("block");
			this.setReservaConfirmada("none");
			this.setReservaYaConfirmada("none");
			this.setLote("");
		}else if (ContVacunatorio.findReservaConfirmada(codigo) == null) {
			String nombre = ConsultaBean.getConsultaReservaNombre();
			ReservaConfirmada resConf = new ReservaConfirmada(codigo, lote, "pendiente", nombre);
			ContVacunatorio.saveReservaConfirmada(resConf);
			this.setElegirLote("none");
			this.setReservaConfirmada("block");
			this.setReservaYaConfirmada("none");
			this.setLote("");
		} else {
			this.setReservaYaConfirmada("block");
			this.setElegirLote("none");
			this.setReservaConfirmada("none");
			this.setLote("");
		}
	}
	
	public String getElegirLote() {
		return elegirLote;
	}
	public void setElegirLote(String elegirLote) {
		this.elegirLote = elegirLote;
	}
	public String getReservaConfirmada() {
		return reservaConfirmada;
	}
	public void setReservaConfirmada(String reservaConfirmada) {
		this.reservaConfirmada = reservaConfirmada;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getReservaYaConfirmada() {
		return reservaYaConfirmada;
	}

	public void setReservaYaConfirmada(String reservaYaConfirmada) {
		this.reservaYaConfirmada = reservaYaConfirmada;
	}
	
	
}
