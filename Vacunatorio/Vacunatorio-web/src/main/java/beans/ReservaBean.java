package beans;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Estado;
import datos.entidades.Reserva;
import datos.entidades.ReservaConfirmada;

@Named("ReservaBean")
@ViewScoped
public class ReservaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final double tamanoPagina = 5.0d;
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	private String nomVacuna = null;
	private int cantidadPaginasVacunados;
    private int paginaActualVacunados = 0;
    private List<Reserva> reservasVacunados;
    private int cantidadPaginasPendientes;
    private int paginaActualPendientes = 0;
    private List<Reserva> reservasPendientes;
    private int cantidadPaginasCancelados;
    private int paginaActualCancelados = 0;
    private List<Reserva> reservasCancelados;
    private int cantidadPaginasConfirmados;
    private int paginaActualConfirmados = 0;
    private List<Reserva> reservasConfirmados;
	
	@EJB
	logica.servicios.local.VacunatorioControllerLocal ContVacunatorio;
	
	public List<Reserva> realizarBusquedaPendientes() {
		//this.reservasPendientes = ContVacunatorio.listarPendientes(paginaActualPendientes * (int) tamanoPagina, (int) tamanoPagina);
		List<Reserva> res = ContVacunatorio.listarPendientes(paginaActualPendientes * (int) tamanoPagina, (int) tamanoPagina, busqueda);
		return res;
	}
	
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
		/*Date fechaVacuna = Date.from(reserva.getIntervalo().getFechayHora().atZone(ZoneId.systemDefault()).toInstant());
		Date now = new Date();*/
		
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
	
	public void anteriorVacunados() {		
        realizarBusquedaVacunados(paginaActualVacunados - 1);
    }

    public void siguienteVacunados() {
        realizarBusquedaVacunados(paginaActualVacunados + 1);
    }
    
    public void anteriorPendientes() {
    	Long cantidadReservas = ContVacunatorio.calcularCantidad(Estado.PENDIENTE);
    	this.cantidadPaginasPendientes = (int) Math.ceil(cantidadReservas/this.tamanoPagina);
    	if((paginaActualPendientes - 1) < cantidadPaginasPendientes && (paginaActualPendientes - 1) >= 0) {
			paginaActualPendientes = (paginaActualPendientes - 1);
		}
        realizarBusquedaPendientes();
    }

    public void siguientePendientes() {
    	Long cantidadReservas = ContVacunatorio.calcularCantidad(Estado.PENDIENTE);
    	this.cantidadPaginasPendientes = (int) Math.ceil(cantidadReservas/this.tamanoPagina);
    	if((paginaActualPendientes + 1) < cantidadPaginasPendientes && (paginaActualPendientes + 1) >= 0) {
			paginaActualPendientes = (paginaActualPendientes + 1);
		}
        realizarBusquedaPendientes();
    }
    
    public void anteriorCancelados() {
        realizarBusquedaCancelados(paginaActualCancelados - 1);
    }

    public void siguienteCancelados() {
        realizarBusquedaCancelados(paginaActualCancelados + 1);
    }
    
    public void anteriorConfirmados() {
        realizarBusquedaConfirmados(paginaActualConfirmados - 1);
    }

    public void siguienteConfirmados() {
        realizarBusquedaConfirmados(paginaActualConfirmados + 1);
    }
    
	public double getTamanoPagina() {
		return tamanoPagina;
	}
	
	public void realizarBusquedaVacunados(int pagina) {
        Long cantidadReservas = ContVacunatorio.calcularCantidad(Estado.VACUNADO);
        this.cantidadPaginasVacunados = (int) Math.ceil(cantidadReservas/this.tamanoPagina);
        if(pagina < cantidadPaginasVacunados && pagina >= 0) {
            paginaActualVacunados = pagina;
        }
        this.reservasVacunados = ContVacunatorio.listarVacunados(paginaActualVacunados * (int) tamanoPagina, (int) tamanoPagina, busqueda);
    }
	
	
	public void realizarBusquedaCancelados(int pagina) {
        Long cantidadReservas = ContVacunatorio.calcularCantidad(Estado.CANCELADA);
        this.cantidadPaginasCancelados = (int) Math.ceil(cantidadReservas/this.tamanoPagina);
        if(pagina < cantidadPaginasCancelados && pagina >= 0) {
            paginaActualCancelados = pagina;
        }
        this.reservasCancelados = ContVacunatorio.listarCancelados(paginaActualCancelados * (int) tamanoPagina, (int) tamanoPagina, busqueda);
    }
	
	public void realizarBusquedaConfirmados(int pagina) {
        Long cantidadReservas = ContVacunatorio.calcularCantidadConfirmadas();
        this.cantidadPaginasConfirmados = (int) Math.ceil(cantidadReservas/this.tamanoPagina);
        if(pagina < cantidadPaginasConfirmados && pagina >= 0) {
            paginaActualConfirmados = pagina;
        }
        this.reservasConfirmados = ContVacunatorio.listarConfirmados(paginaActualConfirmados * (int) tamanoPagina, (int) tamanoPagina, busqueda);
	}
	
	public int getPaginaActualVacunados() {
		return paginaActualVacunados;
	}
	public void setPaginaActualVacunados(int paginaActualVacunados) {
		this.paginaActualVacunados = paginaActualVacunados;
	}
	public List<Reserva> getReservasVacunados() {
		return reservasVacunados;
	}
	public void setReservasVacunados(List<Reserva> reservasVacunados) {
		this.reservasVacunados = reservasVacunados;
	}
	public int getCantidadPaginasVacunados() {
		return cantidadPaginasVacunados;
	}
	public void setCantidadPaginasVacunados(int cantidadPaginasVacunados) {
		this.cantidadPaginasVacunados = cantidadPaginasVacunados;
	}
	public int getCantidadPaginasPendientes() {
		return cantidadPaginasPendientes;
	}
	public void setCantidadPaginasPendientes(int cantidadPaginasPendientes) {
		this.cantidadPaginasPendientes = cantidadPaginasPendientes;
	}
	public int getPaginaActualPendientes() {
		return paginaActualPendientes;
	}
	public void setPaginaActualPendientes(int paginaActualPendientes) {
		this.paginaActualPendientes = paginaActualPendientes;
	}
	public List<Reserva> getReservasPendientes() {
		return reservasPendientes;
	}
	public void setReservasPendientes(List<Reserva> reservasPendientes) {
		this.reservasPendientes = reservasPendientes;
	}
	public int getCantidadPaginasCancelados() {
		return cantidadPaginasCancelados;
	}
	public void setCantidadPaginasCancelados(int cantidadPaginasCancelados) {
		this.cantidadPaginasCancelados = cantidadPaginasCancelados;
	}
	public int getPaginaActualCancelados() {
		return paginaActualCancelados;
	}
	public void setPaginaActualCancelados(int paginaActualCancelados) {
		this.paginaActualCancelados = paginaActualCancelados;
	}
	public List<Reserva> getReservasCancelados() {
		return reservasCancelados;
	}
	public void setReservasCancelados(List<Reserva> reservasCancelados) {
		this.reservasCancelados = reservasCancelados;
	}
	public int getCantidadPaginasConfirmados() {
		return cantidadPaginasConfirmados;
	}
	public void setCantidadPaginasConfirmados(int cantidadPaginasConfirmados) {
		this.cantidadPaginasConfirmados = cantidadPaginasConfirmados;
	}
	public int getPaginaActualConfirmados() {
		return paginaActualConfirmados;
	}
	public void setPaginaActualConfirmados(int paginaActualConfirmados) {
		this.paginaActualConfirmados = paginaActualConfirmados;
	}
	public List<Reserva> getReservasConfirmados() {
		return reservasConfirmados;
	}
	public void setReservasConfirmados(List<Reserva> reservasConfirmados) {
		this.reservasConfirmados = reservasConfirmados;
	}
}
