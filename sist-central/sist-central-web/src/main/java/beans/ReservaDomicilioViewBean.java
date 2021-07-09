package beans;


import datos.entidades.*;
import logica.servicios.local.EnfermedadServiceLocal;
import logica.servicios.local.ReservaDomicilioBeanServiceLocal;
import lombok.Data;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named("ReservaDomicilioViewBean")
@SessionScoped
@Data
public class ReservaDomicilioViewBean implements Serializable {

    @EJB
    EnfermedadServiceLocal enfermedadServiceLocal;

    private String direccion, localidad;

    private Departamento departamento;

    private LocalDate fecha;

    private List<Departamento> departamentos;

    private String enfermedad;

    private List<String> enfermedades;

    @Inject
    private UsuarioLogueadoBean usuarioLogueadoBean;


    @EJB
    ReservaDomicilioBeanServiceLocal reservaDomicilioBeanServiceLocal;


    @PostConstruct
    public void init(){
        departamentos = Arrays.asList(Departamento.values());
        enfermedades  = enfermedadServiceLocal.find().stream().map(Enfermedad::getNombre).collect(Collectors.toList());
    }


    public void comprar(){

        if (fecha.isBefore(LocalDate.now())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha debe ser posterior a hoy"));
            return;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

        int ci = usuarioLogueadoBean.getCiudadano().getCi();
        session.setAttribute("ci", ci);
        session.setAttribute("direccion", direccion);
        session.setAttribute("localidad", localidad);
        session.setAttribute("departamento", departamento);
        session.setAttribute("fecha", fecha);
        session.setAttribute("enfermedad", enfermedad);

        PrimeFaces.current().executeScript(
                "$('#comprarPaypal').submit()"
        );


    }

    public void onFecha(){

    }

    public List<ReservaDomicilio> getReservas(){
        int ci = usuarioLogueadoBean.getCiudadano().getCi();
        return reservaDomicilioBeanServiceLocal.findReservasDomicilioCiudadano(0,Integer.MAX_VALUE, ci).stream()
                .filter(reservaDomicilio -> reservaDomicilio.getEstadoVacunacion() != Estado.CANCELADA).collect(Collectors.toList());
    }

    public List<ReservaDomicilio> getReservasAutoridad() {
        List<ReservaDomicilio> reservasDomicilio = reservaDomicilioBeanServiceLocal.findReservasDomicilio(0, Integer.MAX_VALUE);
        List<ReservaDomicilio> collect = reservasDomicilio.stream()
                .filter(reservaDomicilio -> reservaDomicilio.getEstadoVacunacion() != Estado.CANCELADA).collect(Collectors.toList());
        return collect;
    }




    public void cancelar(int codigo){
        reservaDomicilioBeanServiceLocal.rechazarReservaDomicilioVacunacion(codigo);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void aprobar(int codigo){
        reservaDomicilioBeanServiceLocal.aceptarReservaDomicilio(codigo);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rechazar(int codigo){
        reservaDomicilioBeanServiceLocal.rechazarReservaDomicilio(codigo);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void vacunado(int codigo){
        reservaDomicilioBeanServiceLocal.aceptarReservaDomicilioVacunacion(codigo);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
