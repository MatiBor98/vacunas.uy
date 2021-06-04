package laboratorio.tse;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

@Named("AutenticacionBean")
@RequestScoped
public class AutenticacionBean {


	String nombre;

	public void autenticar() {
		String result;
		Client client = ClientBuilder.newClient();
			WebTarget target = client
					.target("http://localhost:8080/rest/socioLogistico/sociologistico/" + nombre);

			Invocation invocation = target.request().buildGet();
			Response response = invocation.invoke();

			result = (String) response.readEntity(new GenericType<String>() {});
			if(result.equals("0")) {
				
			} else {
				try {
					//FacesContext.getCurrentInstance().getExternalContext().dispatch("/transportarLotes.xhtml");
					FacesContext.getCurrentInstance().getExternalContext().redirect("transportarLotes.xhtml");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//
			}

	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
