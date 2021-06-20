package webServices.rest;

import datos.dtos.VacunatorioEnfermedadesDTO;
import datos.entidades.*;
import logica.servicios.local.SocioLogisticoControllerLocal;
import logica.servicios.local.VacunatorioControllerLocal;
import org.eclipse.microprofile.config.Config;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@RequestScoped
@Path("/socioLogistico")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RestSocioLogistico {

	@EJB
	SocioLogisticoControllerLocal socioLogisticoControllerLocal;
	@Inject
	private Config config;

	public RestSocioLogistico() {

	}
	
	@GET
	@Path("/sociologistico/{nombre}")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response habilitarSocioLogistico(@PathParam("nombre") String nombre) {
		String[] partes= nombre.split("\\|");
		if(partes.length == 2) {
			try{
			String mySecret = config.getValue(partes[0] + ".secretKey", String.class);
			String restSecret = partes[1];
			String nombreSocio = partes[0];
			if(mySecret.equals(restSecret) && socioLogisticoControllerLocal.find(nombreSocio).isPresent()) {
				socioLogisticoControllerLocal.habilitarSocioLogistico(nombreSocio);
				int val = Integer.parseInt(socioLogisticoControllerLocal.getProperty()) + 1;
				socioLogisticoControllerLocal.setProperty(String.valueOf(val));
				return Response.ok(socioLogisticoControllerLocal.getProperty()).build();
			}
			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}
		}

		return Response.ok("0").build();


	}


}
