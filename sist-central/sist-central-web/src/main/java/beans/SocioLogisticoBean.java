package beans;



import datos.entidades.Lote;
import datos.entidades.SocioLogistico;
import datos.entidades.Vacunatorio;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

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

	public Boolean getRealizarBusqueda() {
		return realizarBusqueda;
	}
	public void setRealizarBusqueda(Boolean realizarBusqueda) {
		this.realizarBusqueda = realizarBusqueda;
		if ((this.busqueda != null) && (!this.busqueda.equals(""))) {
			this.hayBusqueda = "block";
		} else {
			this.hayBusqueda = "none";
		}
	}

	public List<String> getNombresSociosLogisticosHabilitados() {
		List<SocioLogistico> socs = ContSocioLogistico.findHabilitados();
		List<String> nombreSocs = new ArrayList<>();
		for (SocioLogistico soc:socs) {
			nombreSocs.add(soc.getNombre());
		}
		return nombreSocs;
	}

	public List<SocioLogistico> getSociosLogisticos() {
		List<SocioLogistico> res = new ArrayList<>();
		List<SocioLogistico> socs = (List<SocioLogistico>) ContSocioLogistico.find();
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (SocioLogistico soc : socs) {
				Matcher match = pattern.matcher(soc.getNombre());
				boolean matchNombre = match.find();
				if (matchNombre) {
					res.add(soc);
				}
			}
		} else {
			res = socs;
		}
		return res;
	}

	public String haySociosLogisticos() {
		String res;
		List<SocioLogistico> socs = (List<SocioLogistico>) ContSocioLogistico.find();
		if (socs.isEmpty()) {
			res = "block";

		} else {
			res = "none";
		}
		return res;
	}

	public String getSocioLogisticoEliminado() {
		return socioLogisticoEliminado;
	}

	public void setSocioLogisticoEliminado(String socioLogisticoEliminado) {
		this.socioLogisticoEliminado = socioLogisticoEliminado;
	}
	public String getSocioLogisticoNoEliminado() {
		return socioLogisticoNoEliminado;
	}
	public void setSocioLogisticoNoEliminado(String socioLogisticoNoEliminado) {
		this.socioLogisticoNoEliminado = socioLogisticoNoEliminado;
	}
	public String getHayBusqueda() {
		return hayBusqueda;
	}

	public void setHayBusqueda(String hayBusqueda) {
		this.hayBusqueda = hayBusqueda;
	}

	public String getColor() {
		if (this.color.equals("white")) {
			this.color = "#222938";
			this.colorSecundario = "white";
		} else {
			this.color = "white";
			this.colorSecundario = "#222938";
		}
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColorSecundario() {
		return colorSecundario;
	}

	public void setColorSecundario(String colorSecundario) {
		this.colorSecundario = colorSecundario;
	}

	public List<Lote> getLotes(String nombre) {
		return ContSocioLogistico.getLotes(nombre);
	}
}
