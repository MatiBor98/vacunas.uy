package beans;

import datos.dtos.VacunatorioTieneAgendaParaEtapaDTO;
import logica.servicios.local.AgendaServiceLocal;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("agendaListarBean")
@ViewScoped
public class AgendaListarBean implements Serializable {
    private int cantidadPaginas;
    private int paginaActual;


    @EJB
    private AgendaServiceLocal agendaServiceLocal;
    private String color = "white";
    private String colorSecundario = "#222938";
    private String nombrePlan = "";
    private List<VacunatorioTieneAgendaParaEtapaDTO> agendas;

    public AgendaListarBean() {
    }

    @PostConstruct
    void postConstruct() {
        realizarBusqueda(0);
    }

    public void realizarBusqueda(int pagina) {
        long cantidadTurnos = agendaServiceLocal.findCount(nombrePlan);
        this.cantidadPaginas = (int) Math.ceil(cantidadTurnos / 3.0);
        if (pagina < cantidadPaginas && pagina >= 0) {
            paginaActual = pagina;
        }
        this.agendas = StringUtils.isBlank(nombrePlan) ?
                agendaServiceLocal.find(paginaActual * 3, 3) :
                agendaServiceLocal.findByNombrePlan(paginaActual * 3, 3, nombrePlan);
    }

    public void eliminarAgenda(int agendaId) {
        agendaServiceLocal.eliminar(agendaId);
    }

    public void anterior() {
        realizarBusqueda(paginaActual - 1);
    }

    public void siguiente() {
        realizarBusqueda(paginaActual + 1);
    }

    public List<VacunatorioTieneAgendaParaEtapaDTO> getAgendas() {
        return this.agendas;
    }

    public String buscarPorNombrePlan() {
        realizarBusqueda(paginaActual);
        return null;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
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

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public int getCantidadPaginas() {
        return cantidadPaginas;
    }
}
