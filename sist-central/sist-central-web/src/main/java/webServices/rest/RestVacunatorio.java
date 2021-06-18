package webServices.rest;

import logica.schedule.DatosVacunatorio;

import logica.servicios.local.VacunatorioControllerLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@RequestScoped
@Path("/vacunatorios")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RestVacunatorio {

	@EJB
	VacunatorioControllerLocal vacControllerLocal;


	public RestVacunatorio() {

	}
	
	@GET
	@Path("/vacunatorio/{nombreVac}")
	public DatosVacunatorio getDatosVacunatorio(@PathParam("nombreVac") String nombreVacunatorio) {
		nombreVacunatorio = nombreVacunatorio.replace("_", " ");
		return vacControllerLocal.getDatosVacunatorio(nombreVacunatorio);
	}

	@GET
	@Path("/vacunatorio2/{nombreVac}")
	public Response getDatosVacunatorio2(@PathParam("nombreVac") String nombreVacunatorio) {
		return Response.ok(vacControllerLocal.getDatosVacunatorio(nombreVacunatorio)).build();

	}

}
