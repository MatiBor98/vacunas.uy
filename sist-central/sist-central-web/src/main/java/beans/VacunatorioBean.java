package beans;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

import datos.entidades.*;

@Named("VacunatorioBean")
@ViewScoped
public class VacunatorioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String dirVacunatorio = null;
	private String vacunatorioYaExiste = "none";
	private String elegirDepartamento = "none";
	private String vacunatorioAgregado = "none";
	private String vacunatorioEliminado = "none";
	private String vacunatorioNoEliminado = "none";
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	private String nomVacunatorio = null;
	private String ciudadVacunatorio = null;
	private String depVacunatorio = null;
	private boolean ubicacion;
	private Double ubicacionX = null; 
	private Double ubicacionY = null;

	@EJB
	logica.servicios.local.VacunatorioControllerLocal ContVacunatorio;
	
	public List<String> getNombresDepartamentos() {
		return ContVacunatorio.getNombresDepartamentos();
	}
	public void agregarVacunatorio() {

		
		if (ContVacunatorio.find(nomVacunatorio).isPresent()) {
			this.setVacunatorioYaExiste("block");
			this.setVacunatorioAgregado("none");
			this.setElegirDepartamento("none");
		} else if (depVacunatorio == null || depVacunatorio.equals("")) {
			this.setElegirDepartamento("block");
			this.setVacunatorioYaExiste("none");
			this.setVacunatorioAgregado("none");
		} else {
			String password = UUID.randomUUID().toString();
			depVacunatorio = depVacunatorio.replaceAll("\\s", "");
			Departamento dep = Departamento.valueOf(depVacunatorio);
			if (ubicacion) {
				GeometryFactory geomFactory = new GeometryFactory( new PrecisionModel(PrecisionModel.FLOATING), 4326);
				double x = ubicacionX.doubleValue();
				double y = ubicacionY.doubleValue();
				ContVacunatorio.addVacunatorio(nomVacunatorio, ciudadVacunatorio, dirVacunatorio, dep, password);
			}else {
				ContVacunatorio.addVacunatorio(nomVacunatorio, ciudadVacunatorio, dirVacunatorio, dep, password);
			}
			this.setElegirDepartamento("none");
			this.setVacunatorioYaExiste("none");
			this.setVacunatorioAgregado("block");
		}
		this.setNomVacunatorio("");
		this.setDirVacunatorio("");
		this.setDepVacunatorio(null);
		this.setCiudadVacunatorio("");
	}
	/*public void eliminarVacunatorio(String nom) {
		if (!ContVacunatorio.find(nom).isEmpty()) {
			ContVacunatorio.eliminar(nom);
			this.setVacunatorioEliminado("block");
			this.setVacunatorioNoEliminado("none");
		} else {
			this.setVacunatorioEliminado("none");
			this.setVacunatorioNoEliminado("block");

		}
	}*/
	
	public List<Vacunatorio> getVacunatorios() {
		List<Vacunatorio> res = new ArrayList<>();
		List<Vacunatorio> vacs = (List<Vacunatorio>) ContVacunatorio.find();
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (Vacunatorio vac : vacs) {
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
	public String hayVacunatorios() {
		String res;
		List<Vacunatorio> vacs = (List<Vacunatorio>) ContVacunatorio.find();
		if (vacs.isEmpty()) {
			res = "block";
			
		} else {
			res = "none";
		}
		return res;
	}
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
	
	public List<String> getNombresVacunatorios() {
		List<Vacunatorio> vacs = getVacunatorios();
		List<String> nombreVacs = new ArrayList<>();
		for (Vacunatorio vac:vacs) {
			nombreVacs.add(vac.getNombre());
		}
		return nombreVacs;
	}
	public String getDirVacunatorio() {
		return dirVacunatorio;
	}
	public void setDirVacunatorio(String dirVacunatorio) {
		this.dirVacunatorio = dirVacunatorio;
	}
	public String getVacunatorioYaExiste() {
		return vacunatorioYaExiste;
	}
	public void setVacunatorioYaExiste(String vacunatorioYaExiste) {
		this.vacunatorioYaExiste = vacunatorioYaExiste;
	}
	public String getElegirDepartamento() {
		return elegirDepartamento;
	}
	public void setElegirDepartamento(String elegirDepartamento) {
		this.elegirDepartamento = elegirDepartamento;
	}
	public String getVacunatorioAgregado() {
		return vacunatorioAgregado;
	}
	public void setVacunatorioAgregado(String vacunatorioAgregado) {
		this.vacunatorioAgregado = vacunatorioAgregado;
	}
	public String getVacunatorioEliminado() {
		return vacunatorioEliminado;
	}
	public void setVacunatorioEliminado(String vacunatorioEliminado) {
		this.vacunatorioEliminado = vacunatorioEliminado;
	}
	public String getVacunatorioNoEliminado() {
		return vacunatorioNoEliminado;
	}
	public void setVacunatorioNoEliminado(String vacunatorioNoEliminado) {
		this.vacunatorioNoEliminado = vacunatorioNoEliminado;
	}
	public String getNomVacunatorio() {
		return nomVacunatorio;
	}
	public void setNomVacunatorio(String nomVacunatorio) {
		this.nomVacunatorio = nomVacunatorio;
	}
	public String getCiudadVacunatorio() {
		return ciudadVacunatorio;
	}
	public void setCiudadVacunatorio(String ciudadVacunatorio) {
		this.ciudadVacunatorio = ciudadVacunatorio;
	}
	public String getDepVacunatorio() {
		return depVacunatorio;
	}
	public void setDepVacunatorio(String depVacunatorio) {
		this.depVacunatorio = depVacunatorio;
	}
	public String getDepartamento(String nombre) {
		Optional<Vacunatorio> vac = ContVacunatorio.find(nombre);
		String dep = vac.get().getDepartamento().toString();
		return dep;
	}
	public String getDireccion(String nombre) {
		Optional<Vacunatorio> vac = ContVacunatorio.find(nombre);
		return vac.get().getDireccion();
	}
	public String getCiudad(String nombre) {
		Optional<Vacunatorio> vac = ContVacunatorio.find(nombre);
		return vac.get().getCiudad();
	}
	
	public List<PuestoVacunacion> getPuestos(String nombre) {
		Optional<Vacunatorio> vac = ContVacunatorio.find(nombre);
		return vac.get().getPuestosVacunacion();
	}
	
	public List<Turno> getTurnos(String nombre) {
		Optional<Vacunatorio> vac = ContVacunatorio.find(nombre);
		return vac.get().getTurnos();
	}

	public Set<Lote> getLotes(String nombre) {
		Optional<Vacunatorio> vac = ContVacunatorio.find(nombre);
		return vac.get().getLotes();
	}
	
	public Double getUbicacionX() {
		return ubicacionX;
	}
	public void setUbicacionX(Double ubicacionX) {
		this.ubicacionX = ubicacionX;
	}
	public Double getUbicacionY() {
		return ubicacionY;
	}
	public void setUbicacionY(Double ubicacionY) {
		this.ubicacionY = ubicacionY;
	}
	public boolean isUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(boolean ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	
	
}
