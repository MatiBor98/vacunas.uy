package webServices.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import datos.dtos.CiudadanoDTO;
import datos.dtos.VacunatorioEnfermedadesDTO;
import datos.entidades.*;
import datos.exceptions.CiudadanoNoEncontradoException;
import datos.exceptions.CiudadanoRegistradoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import logica.creacion.CiudadanoDTOBuilder;
import logica.servicios.local.CiudadanoServiceLocal;
import logica.servicios.local.VacunatorioControllerLocal;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.stream.Collectors.toList;

@RequestScoped
@Path("/movil")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RestMovil {

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

        String[] chunks = jwtIdToken.split("\\.");

        Base64.Decoder decoder = Base64.getDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        SignatureAlgorithm sa = SignatureAlgorithm.RS256;

        /*
        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];
        */

        JsonObject claims = new Gson().fromJson(payload, JsonObject.class);

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


    @GET
    @Path("/notificar/{ci}")
    public String prueba(@PathParam("ci") String ci) {
        try {
            ciudadanoServiceLocal.notificar(Integer.parseInt(ci));
            return "notificado";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }


}
