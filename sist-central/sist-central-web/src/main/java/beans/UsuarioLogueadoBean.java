package beans;

import Utilities.TokenVerifier;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import datos.dtos.CiudadanoDTO;
import datos.exceptions.CiudadanoNoEncontradoException;
import datos.exceptions.CiudadanoRegistradoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import logica.servicios.local.CiudadanoServiceLocal;
import plataformainteroperabilidad.Ciudadano;
import plataformainteroperabilidad.Ciudadanos;
import plataformainteroperabilidad.CiudadanosService;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Named("UsuarioLogueadoBean")
@ViewScoped
public class UsuarioLogueadoBean implements Serializable {

    @EJB
    private CiudadanoServiceLocal usuarios;
    private String email;
    private String userName;
    private String cid;
    private CiudadanoDTO ciudadano;
    private Ciudadano ciudadanoPlataforma;

    @PostConstruct
    public void init() {
        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get("JWT");
        if(cookie!= null) {

            TokenVerifier tokenVerifier = new TokenVerifier();
            String jwtIdToken = tokenVerifier.getAtributeFromJWTString(cookie.getValue(),"id_token");
            Map<String, Claim> claimMap = tokenVerifier.performActionWithFreshToken(jwtIdToken, FacesContext.getCurrentInstance().getExternalContext());

            userName = claimMap.get("nombre_completo").asString();
            userName = org.apache.commons.lang3.StringEscapeUtils.unescapeJava(userName);

            email = claimMap.get("email").asString();
            cid = claimMap.get("numero_documento").asString();

            try {
                ciudadano = usuarios.findByNombreCi(Integer.parseInt(cid));
                final CiudadanosService ciudadanosService = new CiudadanosService();
                Ciudadanos ciudadanosPort = ciudadanosService.getCiudadanosPort();
                ciudadanoPlataforma = ciudadanosPort.obtPersonaPorDoc(this.ciudadano.getCi());
            } catch (CiudadanoNoEncontradoException e) {

                CiudadanoDTO ciud = new CiudadanoDTO(Integer.parseInt(cid),userName,email,false);
                try {
					usuarios.save(ciud);
				} catch (CiudadanoRegistradoException e1) {
					e1.printStackTrace();
				}

                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/frontoffice/cambioEmail.xhtml");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
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



    public CiudadanoDTO getCiudadano() {
        return ciudadano;
    }

    public Ciudadano getCiudadanoPlataforma() {
        return ciudadanoPlataforma;
    }
}

