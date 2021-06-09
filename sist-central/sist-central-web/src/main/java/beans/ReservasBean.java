package beans;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Reserva;
import logica.negocios.ReservaBean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("ReservasBean")
@RequestScoped
public class ReservasBean implements Serializable {
    private final double tamanoPagina = 2.0d;

    @EJB
    private ReservaBean reservaBean;

    @Inject
    private UsuarioLogueadoBean usuarioLogueadoBean;

    private String busqueda = "";
    private List<Reserva> reservas;
    private int cantidadPaginas;
    private int paginaActual = 0;
    private int codigoReserva = 0;

    public int getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(int codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void realizarBusqueda(int pagina) {
        CiudadanoDTO ciudadano = usuarioLogueadoBean.getCiudadano();
        //TODO: Esto es feo, pero quedo así para poder usar @RequestScoped
        // - Cancela la reserva si se recargo porque se apreta 'Cancelar'
        if(codigoReserva > 0) {
            reservaBean.cancelar(ciudadano.getCi(), codigoReserva);
        }

        Long cantidadReservas = reservaBean.listarCount(ciudadano.getCi());
        this.cantidadPaginas = (int) Math.ceil(cantidadReservas/this.tamanoPagina);
        if(pagina < cantidadPaginas && pagina >= 0) {
            paginaActual = pagina;
        }
        this.reservas = reservaBean.listar(paginaActual * (int) tamanoPagina, (int) tamanoPagina, ciudadano.getCi());
    }

    public void anterior() {
        realizarBusqueda(paginaActual - 1);
    }

    public void siguiente() {
        realizarBusqueda(paginaActual + 1);
    }
}
