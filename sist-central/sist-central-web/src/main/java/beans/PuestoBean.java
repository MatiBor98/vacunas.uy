package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import datos.entidades.Enfermedad;
import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;
import datos.entidades.Vacunatorio;

@Named("PuestoBean")
@RequestScoped
public class PuestoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String vacunatorio;
	private String nomPuesto;
	private String puestoYaExiste = "none";
	private String puestoAgregado = "none";
	private String puestoEliminado = "none";
	private String puestoNoEliminado = "none";
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	
	@EJB
	logica.servicios.local.PuestoVacunacionBeanLocal puestoService;
	
	public void agregarPuesto(String vac) {
		if (!puestoService.find(vac, nomPuesto).isEmpty()) {
			this.setPuestoYaExiste("block");
			this.setPuestoAgregado("none");
		} else {
			puestoService.addPuestoVacunacion(nomPuesto, vac);
			this.setPuestoYaExiste("none");
			this.setPuestoAgregado("block");
		}
		this.setNomPuesto("");
		this.setVacunatorio("");
	}
	
	/*public void eliminarVacuna(String nom) {
		if (!vacService.findByNombreVacuna(nom).isEmpty()) {
			vacService.eliminar(nom);
			this.setVacunaEliminada("block");
			this.setVacunaNoEliminada("none");
		} else {
			this.setVacunaEliminada("none");
			this.setVacunaNoEliminada("block");

		}
	}*/
	
	/*public List<Vacuna> getVacs() {
		List<Vacuna> res = new ArrayList<>();
		List<Vacuna> vacs = (List<Vacuna>) vacService.find();
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (Vacuna vac : vacs) {
				Matcher match = pattern.matcher(vac.getNombre());
				boolean matchNombre = match.find();
				if (matchNombre) {
					res.add(vac);
				}
			}
		} else {
			res = vacs;
		}
		return res;
	}*/
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
	/*public String hayVacunas() {
		String res;
		List<Vacuna> vacs = (List<Vacuna>) vacService.find();
		if (vacs.isEmpty()) {
			res = "block";
			
		} else {
			res = "none";
		}
		return res;
	}*/
	public String getBusqueda() {
		return busqueda;
	}
	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
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
	public String getHayBusqueda() {
		return hayBusqueda;
	}
	public void setHayBusqueda(String hayBusqueda) {
		this.hayBusqueda = hayBusqueda;
	}
	
	/*public List<String> getNombresVacunas() {
		List<Vacuna> vacs = getVacs();
		List<String> nombreVacs = new ArrayList<>();
		for (Vacuna vac:vacs) {
			nombreVacs.add(vac.getNombre());
		}
		return nombreVacs;
	}*/
	
	/*public String getVacModificar() {
		return vacModificar;
	}
	public void setVacModificar(String vacModificar) {
		this.vacModificar = vacModificar;
	}*/
	/*public String getVacModificar2() {
		return vacModificar2;
	}
	public void setVacModificar2(String vacModificar2) {
		this.vacModificar2 = vacModificar2;
	}*/
	public String getVacunatorio() {
		return vacunatorio;
	}
	public void setVacunatorio(String vacunatorio) {
		this.vacunatorio = vacunatorio;
	}
	public String getNomPuesto() {
		return nomPuesto;
	}
	public void setNomPuesto(String nomPuesto) {
		this.nomPuesto = nomPuesto;
	}
	public String getPuestoYaExiste() {
		return puestoYaExiste;
	}
	public void setPuestoYaExiste(String puestoYaExiste) {
		this.puestoYaExiste = puestoYaExiste;
	}
	public String getPuestoAgregado() {
		return puestoAgregado;
	}
	public void setPuestoAgregado(String puestoAgregado) {
		this.puestoAgregado = puestoAgregado;
	}
	public String getPuestoEliminado() {
		return puestoEliminado;
	}
	public void setPuestoEliminado(String puestoEliminado) {
		this.puestoEliminado = puestoEliminado;
	}
	public String getPuestoNoEliminado() {
		return puestoNoEliminado;
	}
	public void setPuestoNoEliminado(String puestoNoEliminado) {
		this.puestoNoEliminado = puestoNoEliminado;
	}
	
}
