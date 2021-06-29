package webServices.rest;

import logica.schedule.DatosVacunatorio;

import logica.servicios.local.VacunatorioControllerLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@RequestScoped
@Path("/vacunatorios")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class RestVacunatorio {

	@EJB
	VacunatorioControllerLocal vacControllerLocal;


	public RestVacunatorio() {

	}
	
	@GET
	@Path("/vacunatorio/{nombreVac}/{pass}")
	public DatosVacunatorio getDatosVacunatorio(@PathParam("nombreVac") String nombreVacunatorio, @PathParam("pass") String pass) {
		DatosVacunatorio res = null;
		nombreVacunatorio = nombreVacunatorio.replace("_", " ");
		if (vacControllerLocal.passCorrecta(nombreVacunatorio, pass)) {
			res = vacControllerLocal.getDatosVacunatorio(nombreVacunatorio);
		}
		return res;
	}


}
