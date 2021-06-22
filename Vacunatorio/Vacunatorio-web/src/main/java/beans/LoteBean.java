package beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import datos.entidades.Lote;
import datos.entidades.Reserva;
import datos.entidades.ReservaConfirmada;

@Named("LoteBean")
@RequestScoped
public class LoteBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	
	@EJB
	logica.servicios.local.VacunatorioControllerLocal ContVacunatorio;
	
	public List<Lote> getLotes() {
		List<Lote> res = new ArrayList<>();
		List<Lote> lotes = (List<Lote>) ContVacunatorio.findLotes();
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (Lote lote : lotes) {
				Matcher match = pattern.matcher(lote.getNumeroLote().toString());
				boolean matchNombre = match.find();
				if (matchNombre) {
					res.add(lote);
				}
			}
		} else {
			res = lotes;
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
	public String hayLotes() {
		String res;
		List<Lote> reservas = (List<Lote>) ContVacunatorio.findLotes();
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
	
	
	public List<String> getNombresLotesConDosis(String nomVacuna) {
		List<Lote> lotes = ContVacunatorio.findLotes();
		List<String> nombres = new ArrayList<>();
		for(Lote lote:lotes) {
			if(lote.getVacuna().getNombre().equals(nomVacuna) && lote.getDosisDisponibles() > 0 && lote.getFechaVencimiento().isAfter(LocalDate.now()))
				nombres.add(lote.getNumeroLote().toString());
		}
		return nombres;
	}
	
	
}
