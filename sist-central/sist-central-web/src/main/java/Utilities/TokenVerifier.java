package Utilities;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.microprofile.config.Config;
import org.jgroups.logging.Log;
import webServices.rest.RestMovil;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.json.Json;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

public class TokenVerifier {


     public Map<String, Claim> performActionWithFreshToken(String idToken, ExternalContext externalContext) {
        String modulus = "xxp4vgXYZX0_fq7QS8v2_eXa-n03BrUf1CgWIxwjXD95_xQJZIq1UdiEnXvgh1BJhwENnl7-tAX48YWR5b7B4UTfQCpjx3cWeHgxqlPFbw8kyiWfjVe6XTCJioL_qeenifEpeiaU0xZsZJQg9P9c7lVRYmEEFZQ39dSH4MxQJaU";
        String exponent = "AQAB";
        byte[] modulusByte = Base64.getUrlDecoder().decode(modulus);

        BigInteger modulusAsBigInt = new BigInteger(1, modulusByte);
        byte[] exponentByte = Base64.getUrlDecoder().decode(exponent);
        BigInteger exponentAsBigInt = new BigInteger(1, exponentByte);

        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulusAsBigInt, exponentAsBigInt);

        try{
            KeyFactory factory = KeyFactory.getInstance("RSA");
            RSAPublicKey pub = (RSAPublicKey) factory.generatePublic(spec);


            Algorithm algorithm = Algorithm.RSA256( pub, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(idToken);

            return jwt.getClaims();

        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        }
        catch (TokenExpiredException e){
            Cookie jwt = (Cookie) externalContext.getRequestCookieMap().get("JWT");
            JsonObject cookieValue = new Gson().fromJson(jwt.getValue(), JsonObject.class);

            String tokenResponse = getRefreshToken(cookieValue.get("refresh_token").getAsString(), "openid personal_info email document");





            try {
                String nuevoIdToken = new Gson().fromJson(tokenResponse, JsonObject.class).get("id_token").getAsString();
                KeyFactory factory = KeyFactory.getInstance("RSA");
                RSAPublicKey pub = (RSAPublicKey) factory.generatePublic(spec);


                Algorithm algorithm = Algorithm.RSA256(pub, null);
                JWTVerifier verifier = JWT.require(algorithm)
                        .build(); //Reusable verifier instance
                DecodedJWT decodedJWT = verifier.verify(nuevoIdToken);


                Cookie jwtCookie = new Cookie("JWT", tokenResponse.toString());
                jwtCookie.setMaxAge(36000); //expire could be 60 (seconds)
                jwtCookie.setHttpOnly(true);
                jwtCookie.setPath("/");
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addCookie(jwtCookie);


                return decodedJWT.getClaims();
            }
            catch (Exception ex){
                ex.printStackTrace();


                Cookie jwtCookie = new Cookie("JWT", "");
                jwtCookie.setMaxAge(0); //expire could be 60 (seconds)
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addCookie(jwtCookie);

            }
        }
        return null;
    }




         public Map<String, Claim> verifyTokenAndGetClaims(String idToken){
        String modulus = "xxp4vgXYZX0_fq7QS8v2_eXa-n03BrUf1CgWIxwjXD95_xQJZIq1UdiEnXvgh1BJhwENnl7-tAX48YWR5b7B4UTfQCpjx3cWeHgxqlPFbw8kyiWfjVe6XTCJioL_qeenifEpeiaU0xZsZJQg9P9c7lVRYmEEFZQ39dSH4MxQJaU";
        String exponent = "AQAB";
        byte[] modulusByte = Base64.getUrlDecoder().decode(modulus);

        BigInteger modulusAsBigInt = new BigInteger(1, modulusByte);
        byte[] exponentByte = Base64.getUrlDecoder().decode(exponent);
        BigInteger exponentAsBigInt = new BigInteger(1, exponentByte);

        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulusAsBigInt, exponentAsBigInt);

        try{
            KeyFactory factory = KeyFactory.getInstance("RSA");
            RSAPublicKey pub = (RSAPublicKey) factory.generatePublic(spec);


            Algorithm algorithm = Algorithm.RSA256( pub, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(idToken);

            return jwt.getClaims();

        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        }
        catch (TokenExpiredException e){
            //TODO refresh
            e.printStackTrace();

        }
        return null;
    }


     public String getRefreshToken(String refreshToken, String scope){

        String clientId = "890192";
        String clientSecret = "457d52f181bf11804a3365b49ae4d29a2e03bbabe74997a2f510b179";

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://auth-testing.iduruguay.gub.uy/oidc/v1/token");

        Form form = new Form();
        form.param("grant_type", "refresh_token");
        form.param("refresh_token", refreshToken);

        if (scope != null && !scope.isEmpty()) {
            form.param("scope", scope);
        }

        Response jaxrsResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeaderValue(clientId, clientSecret))
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);
        javax.json.JsonObject tokenResponse = jaxrsResponse.readEntity(javax.json.JsonObject.class);

        return tokenResponse.toString();

    }

    protected String getAuthorizationHeaderValue(String clientId, String clientSecret) {
        String token = clientId + ":" + clientSecret;
        String encodedString = Base64.getEncoder().encodeToString(token.getBytes());
        return "Basic " + encodedString;
    }

    public TokenVerifier() {
    }
}
