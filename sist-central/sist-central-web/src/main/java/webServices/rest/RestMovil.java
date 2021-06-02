package webServices.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import datos.dtos.CiudadanoDTO;
import datos.dtos.VacunatorioEnfermedadesDTO;
import datos.dtos.CertificadoVacunacionDTO;
import datos.entidades.*;
import datos.exceptions.CiudadanoNoEncontradoException;
import datos.exceptions.CiudadanoRegistradoException;
import io.jsonwebtoken.SignatureAlgorithm;
import logica.creacion.CertificadoVacunacionCreator;
import logica.creacion.CiudadanoDTOBuilder;
import logica.servicios.local.CiudadanoServiceLocal;
import logica.servicios.local.VacunatorioControllerLocal;
import org.eclipse.microprofile.config.Config;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.stream.Collectors.toList;

@RequestScoped
@Path("/movil")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RestMovil {

    @Inject
    private Config config;

    @EJB
    VacunatorioControllerLocal vacunatorioControllerLocal;

    @EJB
    CiudadanoServiceLocal ciudadanoServiceLocal;

    public RestMovil() {

    }

    @GET
    @Path("/vacunatorios/{departamento}")
    public List<VacunatorioEnfermedadesDTO> getVacunatoriosDepartamento(@PathParam("departamento") String departamento) {
        List<Vacunatorio> vacunatorios = vacunatorioControllerLocal.findByDepartamento(Departamento.valueOf(departamento));
        ArrayList<VacunatorioEnfermedadesDTO> res = new ArrayList<VacunatorioEnfermedadesDTO>();

        //Converter de Vacunatorio a VacunatorioEnfermedadesDTO
        for (Vacunatorio vac : vacunatorios) {
            List<List<Enfermedad>> enfermedades = vac.getLotes().stream().map(Lote::getVacuna).map(Vacuna::getEnfermedades)
                    .collect(toList());


            List<String> enfermedadesFlat = new ArrayList<>();

            for (List<Enfermedad> listaEnfermedades : enfermedades) {
                List<String> stringListaEnfermedades = listaEnfermedades.stream().map(Enfermedad::getNombre).collect(toList());
                enfermedadesFlat.addAll(stringListaEnfermedades);
            }

            enfermedadesFlat = new ArrayList<>(new HashSet<>(enfermedadesFlat)); // Remover duplicados


            res.add(new VacunatorioEnfermedadesDTO(
                    vac.getNombre(), vac.getCiudad(), vac.getDireccion(), vac.getDepartamento(),
                    enfermedadesFlat));
        }

        return res;
    }

    @POST
    @Path("/token/{ci}")
    public String addTokenToCiudadano(@PathParam("ci") String ci, String token) {
        try {

            String decodedToken = java.net.URLDecoder.decode(token, StandardCharsets.UTF_8.name());
            ciudadanoServiceLocal.updateFirebaseTokenMovil(Integer.parseInt(ci), decodedToken);
            return "agregado";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @GET
    @Path("/ciudadanos/busqueda/{ci}")
    public String isCiudadano(@PathParam("ci") String ci) {
        try {
            ciudadanoServiceLocal.findByNombreCi(Integer.parseInt(ci));
            return "true";
        } catch (CiudadanoNoEncontradoException e) {
            return "false";
        }
    }

    @GET
    @Path("/ciudadanos/agregar/")
    public void addCiudadano(@QueryParam("jwt") String jwtIdToken) {

        JsonObject claims = getClaimsFromJWT(jwtIdToken);

        int ci =  Integer.parseInt(claims.get("numero_documento").getAsString());
        String nombre = claims.get("name").getAsString();
        String email = claims.get("email").getAsString();


        CiudadanoDTO ciudadanoDTO = new CiudadanoDTOBuilder().setCi(ci)
                .setNombre(nombre)
                .setVacunador(false).setEmail(email).createCiudadanoDTO();

        try {
			ciudadanoServiceLocal.save(ciudadanoDTO);
		} catch (CiudadanoRegistradoException e) {
			e.printStackTrace();
		}

    }




    @EJB
    CertificadoVacunacionCreator certificadoVacunacionCreator;

    @GET
    @Path("/ciudadanos/certificado/")
    public CertificadoVacunacionDTO getCertificadoVacunacion(@QueryParam("jwt") String jwtIdToken) {

        JsonObject claims = getClaimsFromJWT(jwtIdToken);

        int ci = Integer.parseInt(claims.get("numero_documento").getAsString());
        String nombre = claims.get("name").getAsString();

        return certificadoVacunacionCreator.create(ci);
    }

    private JsonObject getClaimsFromJWT(String jwtIdToken) {
        String[] chunks = jwtIdToken.split("\\.");

        Base64.Decoder decoder = Base64.getDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        SignatureAlgorithm sa = SignatureAlgorithm.RS256;

        /*
        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];
        */

        return new Gson().fromJson(payload, JsonObject.class);
    }

    @GET
    @Path("/gubuy/tokenrequest")
    public String getTokenRequest(@QueryParam("code") String authorizationCode){

        if (authorizationCode == null || authorizationCode.equals("")){
            throw new IllegalArgumentException("código inválido");
        }

        String clientId = config.getValue("client.clientId", String.class);
        String clientSecret = config.getValue("client.clientSecret", String.class);


        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(config.getValue("provider.tokenUri", String.class));
        Form form = new Form();
        form.param("grant_type", "authorization_code");
        form.param("code", authorizationCode);
        form.param("redirect_uri", config.getValue("client.redirectUri", String.class));


        javax.json.JsonObject tokenResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeaderValue(clientId, clientSecret))
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), javax.json.JsonObject.class);
            /////////


        return tokenResponse.toString();


    }

    @GET
    @Path("/gubuy/refreshtoken")
    public String getRefreshToken(@QueryParam("refresh_token") String refreshToken, @QueryParam("scope") String scope){

        String clientId = config.getValue("client.clientId", String.class);
        String clientSecret = config.getValue("client.clientSecret", String.class);

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(config.getValue("provider.tokenUri", String.class));

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

}
