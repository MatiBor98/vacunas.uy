package beans;

import Utilities.TokenVerifier;
import com.auth0.jwt.interfaces.Claim;
import datos.dtos.CiudadanoDTO;
import datos.exceptions.CiudadanoNoEncontradoException;
import jdk.jfr.Name;
import logica.servicios.local.CiudadanoServiceLocal;
import lombok.Data;
import org.primefaces.PrimeFaces;
import plataformainteroperabilidad.Ciudadano;
import plataformainteroperabilidad.Ciudadanos;
import plataformainteroperabilidad.CiudadanosService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.time.LocalDate;
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

    @PostConstruct
    public void initUsuario(){
        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get("JWT");
        TokenVerifier tokenVerifier = new TokenVerifier();
        String jwtIdToken = tokenVerifier.getAtributeFromJWTString(cookie.getValue(), "id_token");
        Map<String, Claim> claimMap = tokenVerifier.performActionWithFreshToken(jwtIdToken, FacesContext.getCurrentInstance().getExternalContext());

        Integer ci = Integer.parseInt(claimMap.get("numero_documento").asString());


        try{
            final CiudadanosService ciudadanosService = new CiudadanosService();
            Ciudadanos ciudadanosPort = ciudadanosService.getCiudadanosPort();
            Ciudadano ciudadanoPlataforma = ciudadanosPort.obtPersonaPorDoc(ci);
            ciudadanoServiceLocal.setSexoFechanacimiento(ci,ciudadanoPlataforma.getSexo(),
                    LocalDate.parse(ciudadanoPlataforma.getFechaNacimiento()), ciudadanoPlataforma.getTrabajadorEscencial());
        }
        catch (Exception e){
            PrimeFaces.current().executeScript("alert('Usted no se encuentra en la base de datos de la plataforma de interoperabiliidad, " +
                    "por favor regularice su situación con el estado uruguayo')");
        }

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
