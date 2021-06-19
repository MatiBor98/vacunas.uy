package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import datos.entidades.Estado;
import datos.entidades.Reserva;
import datos.entidades.ReservaConfirmada;

@Named("ReservaBean")
@RequestScoped
public class ReservaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	private String nomVacuna = null;
	
	@EJB
	logica.servicios.local.VacunatorioControllerLocal ContVacunatorio;
	
	public List<Reserva> getReservas() {
		List<Reserva> res = new ArrayList<>();
		List<Reserva> reservas = (List<Reserva>) ContVacunatorio.findReservas();
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (Reserva reserva : reservas) {
				Matcher match = pattern.matcher(reserva.getCiudadano().getNombre());
				boolean matchNombre = match.find();
				if (matchNombre) {
					res.add(reserva);
				}
			}
		} else {
			res = reservas;
		}
		return res;
	}
	public String getColor() {
		if (this.color.equals("white")) {
			this.color = "#222938";
			this.colorSecundario = "white";
		} else {
			this.color = "white";
			this.colorSecundario = "#222938";
		}
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColorSecundario() {
		return colorSecundario;
	}
	public void setColorSecundario(String colorSecundario) {
		this.colorSecundario = colorSecundario;
	}
	public String hayReservas() {
		String res;
		List<Reserva> reservas = (List<Reserva>) ContVacunatorio.findReservas();
		if (reservas.isEmpty()) {
			res = "block";
			
		} else {
			res = "none";
		}
		return res;
	}
	public String getBusqueda() {
		return busqueda;
	}
	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}
	public Boolean getRealizarBusqueda() {
		return realizarBusqueda;
	}
	public void setRealizarBusqueda(Boolean realizarBusqueda) {
		this.realizarBusqueda = realizarBusqueda;
		if ((this.busqueda != null) && (!this.busqueda.equals(""))) {
			this.hayBusqueda = "block";
		} else {
			this.hayBusqueda = "none";
		}
	}
	public String getHayBusqueda() {
		return hayBusqueda;
	}
	public void setHayBusqueda(String hayBusqueda) {
		this.hayBusqueda = hayBusqueda;
	}
	public String getNomVacuna() {
		return nomVacuna;
	}
	public void setNomVacuna(String nomVacuna) {
		this.nomVacuna = nomVacuna;
	}
	
	public Boolean mostrarBoton(Reserva reserva) {
		Boolean res = false;
		//Date fechaVacuna = Date.from(reserva.getIntervalo().getFechayHora().atZone(ZoneId.systemDefault()).toInstant());
		//Date now = new Date();
		
		if (reserva.getEstado().toString().equals("PENDIENTE") && ContVacunatorio.findReservaConfirmada(reserva.getCodigo()) == null /*&& (fechaVacuna.compareTo(now) == 0)*/) {
			res = true;
			
		}
		return res;
	}
	
	public Boolean esVacunado(Reserva reserva) {
		Boolean res = false;
		if(reserva.getEstado().equals(Estado.VACUNADO)) {
			res = true;
		}
		return res;
	}
	
	public Boolean esPendiente(Reserva reserva) {
		Boolean res = false;
		if (reserva.getEstado().equals(Estado.PENDIENTE) && ContVacunatorio.findReservaConfirmada(reserva.getCodigo()) == null) {
			res = true;
		}
		return res;
	}
	public Boolean esConfirmado(Reserva reserva) {
		Boolean res = false;
		if (reserva.getEstado().equals(Estado.PENDIENTE) && ContVacunatorio.findReservaConfirmada(reserva.getCodigo()) != null) {
			res = true;
		}
		return res;
	}
	public Boolean esCancelado(Reserva reserva) {
		Boolean res = false;
		if(reserva.getEstado().equals(Estado.CANCELADA)) {
			res = true;
		}
		return res;
	}
	
}
