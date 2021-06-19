package beans;

import datos.entidades.Enfermedad;
import datos.entidades.PlanVacunacion;
import datos.entidades.Vacuna;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named("EnfermedadBean")
@RequestScoped
public class EnfermedadBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String[] planesVacunales;
	private String[] vacunas;
	private String enfermedadYaExiste = "none";
	private String enfermedadAgregada = "none";
	private String enfermedadEliminada = "none";
	private String enfermedadNoEliminada = "none";
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	private String nomEnfermedad = null;
	private String descEnfermedad = null;
	
	@EJB
	logica.servicios.local.VacunaServiceLocal vacService;
	@EJB
	logica.servicios.local.EnfermedadServiceLocal enfService;
	
	public void agregarEnfermedad() {
		if (!enfService.findByNombreEnfermedad(nomEnfermedad).isEmpty()) {
			this.setEnfermedadYaExiste("block");
			this.setEnfermedadAgregada("none");
		} else {
			List<Vacuna> vacs = new ArrayList<>();
			List<PlanVacunacion> planVacs = new ArrayList<>();	
			enfService.save(nomEnfermedad, descEnfermedad, vacs, planVacs);
			this.setEnfermedadYaExiste("none");
			this.setEnfermedadAgregada("block");
		}
		this.setNomEnfermedad("");
		this.setDescEnfermedad("");
	}
	
	public void eliminarEnfermedad(String nom) {
		if (!enfService.findByNombreEnfermedad(nom).isEmpty()) {
			enfService.eliminar(nom);
			this.setEnfermedadEliminada("block");
			this.setEnfermedadNoEliminada("none");
		} else {
			this.setEnfermedadEliminada("none");
			this.setEnfermedadNoEliminada("block");

		}
	}
	
	public List<Enfermedad> getEnfs() {
		List<Enfermedad> res = new ArrayList<>();
		List<Enfermedad> enfs = (List<Enfermedad>) enfService.find();
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (Enfermedad enf : enfs) {
				Matcher match = pattern.matcher(enf.getNombre());
				boolean matchNombre = match.find();
				if (matchNombre) {
					res.add(enf);
				}
			}
		} else {
			res = enfs;
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
	public String hayEnfermedades() {
		String res;
		List<Enfermedad> enfs = (List<Enfermedad>) enfService.find();
		if (enfs.isEmpty()) {
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
	
	public List<String> getNombresEnfermedades() {
		List<Enfermedad> enfs = getEnfs();
		List<String> nombreEnfs = new ArrayList<>();
		for (Enfermedad enf:enfs) {
			nombreEnfs.add(enf.getNombre());
		}
		return nombreEnfs;
	}
	public String[] getPlanesVacunales() {
		return planesVacunales;
	}
	public void setPlanesVacunales(String[] planesVacunales) {
		this.planesVacunales = planesVacunales;
	}
	public String[] getVacunas() {
		return vacunas;
	}
	public void setVacunas(String[] vacunas) {
		this.vacunas = vacunas;
	}
	public String getEnfermedadYaExiste() {
		return enfermedadYaExiste;
	}
	public void setEnfermedadYaExiste(String enfermedadYaExiste) {
		this.enfermedadYaExiste = enfermedadYaExiste;
	}
	public String getEnfermedadAgregada() {
		return enfermedadAgregada;
	}
	public void setEnfermedadAgregada(String enfermedadAgregada) {
		this.enfermedadAgregada = enfermedadAgregada;
	}
	public String getEnfermedadEliminada() {
		return enfermedadEliminada;
	}
	public void setEnfermedadEliminada(String enfermedadEliminada) {
		this.enfermedadEliminada = enfermedadEliminada;
	}
	public String getEnfermedadNoEliminada() {
		return enfermedadNoEliminada;
	}
	public void setEnfermedadNoEliminada(String enfermedadNoEliminada) {
		this.enfermedadNoEliminada = enfermedadNoEliminada;
	}
	public String getNomEnfermedad() {
		return nomEnfermedad;
	}
	public void setNomEnfermedad(String nomEnfermedad) {
		this.nomEnfermedad = nomEnfermedad;
	}
	public String getDescEnfermedad() {
		return descEnfermedad;
	}
	public void setDescEnfermedad(String descEnfermedad) {
		this.descEnfermedad = descEnfermedad;
	}
}
