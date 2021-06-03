package beans;

import logica.negocios.CiudadanoBean;
import logica.servicios.local.CiudadanoServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("NotificacionBean")
@RequestScoped
public class NotificacionBean implements Serializable {

    @EJB
    CiudadanoServiceLocal ciudadanoServiceLocal;


    private String titulo;
    private String cuerpo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public NotificacionBean() {

    }


    public void notificar(){
        ciudadanoServiceLocal.notificarTodosLosUsuariosMoviles(titulo,cuerpo);
    }

}
