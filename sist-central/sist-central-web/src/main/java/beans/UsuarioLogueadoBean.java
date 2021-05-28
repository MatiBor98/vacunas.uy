package beans;

import datos.dtos.CiudadanoDTO;
import logica.servicios.local.CiudadanoServiceLocal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Named("UsuarioLogueadoBean")
@ViewScoped
public class UsuarioLogueadoBean implements Serializable {

    @EJB
    CiudadanoServiceLocal usuarios;

    String email;
    String userName;
    String cid;
    @PostConstruct
    public void init() {
        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get("JWT");
        if(cookie!= null) {
            String a = getAtributeFromJWTString(cookie.getValue(),"id_token");
            String[] chunks = a.split("\\.");
            Base64.Decoder decoder = Base64.getDecoder();
            String payload = new String(decoder.decode(chunks[1]));
            userName = getAtributeFromJWTString(payload, "nombre_completo");
            email = getAtributeFromJWTString(payload, "email");
            cid = getAtributeFromJWTString(payload, "numero_documento");
            //preguntarle a agus si iria false o que
            CiudadanoDTO ciud = new CiudadanoDTO(Integer.parseInt(cid),userName,email,false);
            usuarios.save(ciud);
        } else
            email = null;
            userName = null;
            cid = null;

    }

    public String getEmail() {
        return email;
    }

    String getAtributeFromJWTString(String payload, String param){
        String[] user = payload.split(param+"\":\"");
        user = user[1].split("\"");
        return user[0];
    }

}

