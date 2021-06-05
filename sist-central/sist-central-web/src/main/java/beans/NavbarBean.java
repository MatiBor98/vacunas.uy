package beans;


import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("NavbarBean")
@RequestScoped
public class NavbarBean {

    public String esIndex(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("/frontofficeIndex.xhtml") ?
                "active" : "";
    }

    public String esMonitor(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("/frontoffice/Monitor.xhtml") ?
                "active" : "";
    }

    public String esPlanes(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("/frontoffice/Planes.xhtml") ?
                "active" : "";
    }
    public String esReservas(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("/frontoffice/Reservas.xhtml") ?
                "active" : "";
    }

    public String esAgendar(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("/frontoffice/AgendarReserva.xhtml") ?
                "active" : "";
    }
}
