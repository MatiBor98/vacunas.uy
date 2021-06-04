package beans;

import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import datos.dtos.CiudadanoDTO;
import datos.exceptions.CiudadanoNoEncontradoException;
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
import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.net.*;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.*;
import com.nimbusds.oauth2.sdk.id.*;
import com.nimbusds.openid.connect.sdk.validators.*;

@Named("UsuarioLogueadoBean")
@ViewScoped
public class UsuarioLogueadoBean implements Serializable {

    @EJB
    CiudadanoServiceLocal usuarios;

    String email;
    String userName;
    String cid;
    @PostConstruct
    public void init() throws MalformedURLException, ParseException {
        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get("JWT");
        if(cookie!= null) {
            /*Issuer iss = new Issuer("https://auth-testing.iduruguay.gub.uy/oidc/v1/jwks");
            ClientID clientID = new ClientID("890192");
            JWSAlgorithm jwsAlg = JWSAlgorithm.RS256;
            URL jwkSetURL = new URL("https://auth-testing.iduruguay.gub.uy/oidc/v1/jwks");

            // Create validator for signed ID tokens
            IDTokenValidator validator = new IDTokenValidator(iss, clientID, jwsAlg, jwkSetURL);
            String a = getAtributeFromJWTString(cookie.getValue(),"id_token");
            JWT idToken = JWTParser.parse(a);
            Nonce expectedNonce = null; // or null

            IDTokenClaimsSet claims;

            try {
                claims = validator.validate(idToken, expectedNonce);
                System.out.println("Logged in user " + claims.getSubject());
            } catch (BadJOSEException e) {
                e.printStackTrace();
            } catch (JOSEException e) {
                e.printStackTrace();

                // Internal processing exception
            }*/
            String a = getAtributeFromJWTString(cookie.getValue(),"id_token");
            String[] chunks = a.split("\\.");
            Base64.Decoder decoder = Base64.getDecoder();
            String payload = new String(decoder.decode(chunks[1]));
            userName = getAtributeFromJWTString(payload, "nombre_completo");
            email = getAtributeFromJWTString(payload, "email");
            cid = getAtributeFromJWTString(payload, "numero_documento");
            try {
                CiudadanoDTO ciud = usuarios.findByNombreCi(Integer.parseInt(cid));
            } catch (CiudadanoNoEncontradoException e) {
                CiudadanoDTO ciud = new CiudadanoDTO(Integer.parseInt(cid),userName,email,false);
                usuarios.save(ciud);
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

}

