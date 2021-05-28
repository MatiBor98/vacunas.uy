package webServices.rest;

import java.util.ArrayList;
import java.util.HashSet;

import static java.util.stream.Collectors.toList;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import datos.dtos.VacunatorioEnfermedadesDTO;
import datos.entidades.Departamento;
import datos.entidades.Enfermedad;
import datos.entidades.Lote;
import datos.entidades.Vacuna;
import datos.entidades.Vacunatorio;
import logica.servicios.local.CiudadanoServiceLocal;
import logica.servicios.local.VacunatorioControllerLocal;

@RequestScoped
@Path("/movil")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
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
		for (Vacunatorio vac: vacunatorios) {
			List<List<Enfermedad>> enfermedades = vac.getLotes().stream().map(Lote::getVacuna).map(Vacuna::getEnfermedades)
					.collect(toList());
			
			
			List<String> enfermedadesFlat = new ArrayList<>();
			
			for (List<Enfermedad> listaEnfermedades: enfermedades) {
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
