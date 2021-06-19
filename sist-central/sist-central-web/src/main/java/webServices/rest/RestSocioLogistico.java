package webServices.rest;

import logica.servicios.local.SocioLogisticoControllerLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/socioLogistico")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RestSocioLogistico {

	@EJB
	SocioLogisticoControllerLocal socioLogisticoControllerLocal;


	public RestSocioLogistico() {

	}
	
	@GET
	@Path("/sociologistico/{nombre}")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response habilitarSocioLogistico(@PathParam("nombre") String nombre) {
		if(socioLogisticoControllerLocal.find(nombre).isPresent()) {
			socioLogisticoControllerLocal.habilitarSocioLogistico(nombre);
			int val = Integer.parseInt(socioLogisticoControllerLocal.getProperty()) + 1;
			socioLogisticoControllerLocal.setProperty(String.valueOf(val));
			return Response.ok(socioLogisticoControllerLocal.getProperty()).build();
		}
		return Response.ok("0").build();


	}


}
