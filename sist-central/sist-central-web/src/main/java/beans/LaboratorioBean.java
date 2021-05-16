package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import datos.entidades.Enfermedad;
import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;

@Named("LaboratorioBean")
@RequestScoped
public class LaboratorioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String laboratorioYaExiste = "none";
	private String[] vacunas;
	private String laboratorioAgregado = "none";
	private String laboratorioEliminado = "none";
	private String laboratorioNoEliminado = "none";
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	private String nomLaboratorio = null;
	
	@EJB
	logica.servicios.local.LaboratorioServiceLocal labService;
	
	public void agregarLaboratorio() {
		if (!labService.findByNombreLaboratorio(nomLaboratorio).isEmpty()) {
			this.setLaboratorioYaExiste("block");
			this.setLaboratorioAgregado("none");
		} else {
			List<Vacuna> vacs = new ArrayList<>();
			labService.save(nomLaboratorio, vacs);
			this.setLaboratorioAgregado("block");
			this.setLaboratorioYaExiste("none");
		}
		this.setNomLaboratorio("");
	}
	public void eliminarLaboratorio(String nom) {
		if (!labService.findByNombreLaboratorio(nom).isEmpty()) {
			labService.eliminar(nom);
			this.setLaboratorioEliminado("block");
			this.setLaboratorioNoEliminado("none");
		} else {
			this.setLaboratorioEliminado("none");
			this.setLaboratorioNoEliminado("block");

		}
	}
	
	public List<String> getNombresLaboratorios() {
		List<Laboratorio> labs = getLabs();
		List<String> nombreLabs = new ArrayList<>();
		for (Laboratorio lab:labs) {
			nombreLabs.add(lab.getNombre());
		}
		return nombreLabs;
	}
	
	public List<Laboratorio> getLabs() {
		List<Laboratorio> res = new ArrayList<>();
		List<Laboratorio> labs = (List<Laboratorio>) labService.find();
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (Laboratorio lab : labs) {
				Matcher match = pattern.matcher(lab.getNombre());
				boolean matchNombre = match.find();
				if (matchNombre) {
					res.add(lab);
				}
			}
		} else {
			res = labs;
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
	public String hayLaboratorios() {
		String res;
		List<Laboratorio> labs = (List<Laboratorio>) labService.find();
		if (labs.isEmpty()) {
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
	public String getLaboratorioYaExiste() {
		return laboratorioYaExiste;
	}
	public void setLaboratorioYaExiste(String laboratorioYaExiste) {
		this.laboratorioYaExiste = laboratorioYaExiste;
	}
	public String getLaboratorioAgregado() {
		return laboratorioAgregado;
	}
	public void setLaboratorioAgregado(String laboratorioAgregado) {
		this.laboratorioAgregado = laboratorioAgregado;
	}
	public String getLaboratorioEliminado() {
		return laboratorioEliminado;
	}
	public void setLaboratorioEliminado(String laboratorioEliminado) {
		this.laboratorioEliminado = laboratorioEliminado;
	}
	public String getLaboratorioNoEliminado() {
		return laboratorioNoEliminado;
	}
	public void setLaboratorioNoEliminado(String laboratorioNoEliminado) {
		this.laboratorioNoEliminado = laboratorioNoEliminado;
	}
	public String getNomLaboratorio() {
		return nomLaboratorio;
	}
	public void setNomLaboratorio(String nomLaboratorio) {
		this.nomLaboratorio = nomLaboratorio;
	}
	public String[] getVacunas() {
		return vacunas;
	}
	public void setVacunas(String[] vacunas) {
		this.vacunas = vacunas;
	}
}
