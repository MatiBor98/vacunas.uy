package beans;

import datos.dtos.CiudadanoDTO;
import datos.exceptions.CiudadanoNoEncontradoException;
import datos.exceptions.CiudadanoRegistradoException;
import io.jsonwebtoken.security.Keys;
import logica.servicios.local.CiudadanoServiceLocal;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
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
    private CiudadanoServiceLocal usuarios;
    private String email;
    private String userName;
    private String cid;
    private CiudadanoDTO ciudadano;
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
            try {
                ciudadano = usuarios.findByNombreCi(Integer.parseInt(cid));
            } catch (CiudadanoNoEncontradoException e) {
                CiudadanoDTO ciud = new CiudadanoDTO(Integer.parseInt(cid),userName,email,false);
                try {
					usuarios.save(ciud);
				} catch (CiudadanoRegistradoException e1) {
					e1.printStackTrace();
				}
            }
        } else {
            email = null;
            userName = null;
            cid = null;
        }

    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }
    public String getCid() {
        return cid;
    }

    String getAtributeFromJWTString(String payload, String param){
        String[] user = payload.split(param+"\":\"");
        user = user[1].split("\"");
        return user[0];
    }

    public CiudadanoDTO getCiudadano() {
        return ciudadano;
    }
}

