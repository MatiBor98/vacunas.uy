package beans;



import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named("SocioLogisticoBean")
@RequestScoped
public class SocioLogisticoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String socioLogisticoYaExiste = "none";
	private String socioLogisticoAgregado = "none";
	private String socioLogisticoEliminado = "none";
	private String socioLogisticoNoEliminado = "none";
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	private String nomSocioLogistico = null;

	@EJB
	logica.servicios.local.SocioLogisticoControllerLocal ContSocioLogistico;
	
	public void agregarSocioLogistico() {
		if (ContSocioLogistico.find(nomSocioLogistico).isPresent()) {
			this.setSocioLogisticoYaExiste("block");
			this.setSocioLogisticoAgregado("none");
		} else {
			ContSocioLogistico.addSocioLogistico(nomSocioLogistico);
			this.setSocioLogisticoYaExiste("none");
			this.setSocioLogisticoAgregado("block");
		}
		this.setNomSocioLogistico("");
	}

	public String getSocioLogisticoYaExiste() {
		return socioLogisticoYaExiste;
	}
	public void setSocioLogisticoYaExiste(String socioLogisticoYaExiste) {
		this.socioLogisticoYaExiste = socioLogisticoYaExiste;
	}

	public String getSocioLogisticoAgregado() {
		return socioLogisticoAgregado;
	}
	public void setSocioLogisticoAgregado(String socioLogisticoAgregado) {
		this.socioLogisticoAgregado = socioLogisticoAgregado;
	}

	public String getNomSocioLogistico() {
		return nomSocioLogistico;
	}
	public void setNomSocioLogistico(String nomSocioLogistico) {
		this.nomSocioLogistico = nomSocioLogistico;
	}

}
