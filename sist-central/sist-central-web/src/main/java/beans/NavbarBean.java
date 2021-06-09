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
    
    public String esAdmin(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("/backoffice/administrador/Administrador.xhtml") ?
                "active" : "";
    }

    public String esVacunatorios(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("/backoffice/ConsultaVacunatorios.xhtml") ?
                "active" : "";
    }

    public String esSociosLogisticos(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("/backoffice/ConsultaSociosLogisticos.xhtml") ?
                "active" : "";
    }
    public String esUsuariosBackOffice(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("/backoffice/administrador/ConsultaUsuariosBackOffice.xhtml") ?
                "active" : "";
    }

    public String esUsuariosFrontOffice(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("s/backoffice/administrador/ConsultaUsuariosFrontOffice.xhtml") ?
                "active" : "";
    }
    
    public String esEnfermedades(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("s/backoffice/autoridad/ConsultaEnfermedades.xhtml") ?
                "active" : "";
    }
    
    public String esLaboratorios(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("s/backoffice/autoridad/ConsultaLaboratorios.xhtml") ?
                "active" : "";
    }
    
    public String esPlanesDeVacunacion(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("s/backoffice/autoridad/ConsultaPlanesVacunacion.xhtml") ?
                "active" : "";
    }
    
    public String esVacunas(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("s/backoffice/autoridad/ConsultaVacunas.xhtml") ?
                "active" : "";
    }
    
    public String esAutoridad(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().equals("s/backoffice/autoridad/Autoridad.xhtml") ?
                "active" : "";
    }
}
