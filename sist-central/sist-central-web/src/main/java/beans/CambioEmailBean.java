package beans;

import Utilities.TokenVerifier;
import com.auth0.jwt.interfaces.Claim;
import datos.dtos.CiudadanoDTO;
import datos.exceptions.CiudadanoNoEncontradoException;
import logica.servicios.local.CiudadanoServiceLocal;
import lombok.Data;
import org.primefaces.PrimeFaces;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Map;

@Named("CambioEmailBean")
@Data
@RequestScoped
public class CambioEmailBean {

    @EJB
    CiudadanoServiceLocal ciudadanoServiceLocal;

    private String email, emailConf;

    public CambioEmailBean() {
    }

    public void cambiarEmail(){
        if (!email.equals(emailConf)){
            PrimeFaces.current().executeScript("alert('los emails no coinciden')");
            return;
        }

        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get("JWT");
        if(cookie!= null) {

            TokenVerifier tokenVerifier = new TokenVerifier();
            String jwtIdToken = tokenVerifier.getAtributeFromJWTString(cookie.getValue(), "id_token");
            Map<String, Claim> claimMap = tokenVerifier.performActionWithFreshToken(jwtIdToken, FacesContext.getCurrentInstance().getExternalContext());

            Integer.parseInt(claimMap.get("numero_documento").asString());
            CiudadanoDTO ciud = null;
            try {
                ciud = ciudadanoServiceLocal.findByNombreCi(Integer.parseInt(claimMap.get("numero_documento").asString()));
            } catch (CiudadanoNoEncontradoException e) {
                e.printStackTrace();
            }

            ciud.setEmail(email);
            ciudadanoServiceLocal.overwriteCiudadano(ciud);

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Home");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
