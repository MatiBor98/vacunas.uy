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

@Named("VacunaBean")
@RequestScoped
public class VacunaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String vacunaYaExiste = "none";
	private String vacunaAgregada = "none";
	private String vacunaEliminada = "none";
	private String vacunaNoEliminada = "none";
	private String completarCampos = "none";
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	private String nomVacuna = null;
	private String cantDosis = null;
	private String inmunidadMeses = null;
	private String dosisSeparacion = null;
	
	@EJB
	logica.servicios.local.VacunaServiceLocal vacService;
	
	public String getNomVacuna() {
		return nomVacuna;
	}
	public void setNomVacuna(String nomVacuna) {
		this.nomVacuna = nomVacuna;
	}
	public String getCantDosis() {
		return cantDosis;
	}
	public void setCantDosis(String cantDosis) {
		this.cantDosis = cantDosis;
	}
	public String getInmunidadMeses() {
		return cantDosis;
	}
	public void setInmunidadMeses(String inmunidadMeses) {
		this.inmunidadMeses = inmunidadMeses;
	}
	public String getDosisSeparacion() {
		return inmunidadMeses;
	}
	public void setDosisSeparacion(String dosisSeparacion) {
		this.dosisSeparacion = dosisSeparacion;
	}
	
	public void agregarVacuna() {
		if (!vacService.findByNombreVacuna(nomVacuna).isEmpty()) {
			this.setVacunaYaExiste("block");
			this.setVacunaAgregada("none");
		} else {
			int cantDosisNumero;
			int dosisSeparacionNumero;
			List<Laboratorio> labs = new ArrayList<>();
			List<Enfermedad> enfs = new ArrayList<>();
			int inmunidadMesesNumero = Integer.valueOf(inmunidadMeses);		
			if (cantDosis.equals("") && dosisSeparacion.equals("")) {
				cantDosisNumero = 1;
				dosisSeparacionNumero = 0;
				vacService.save(nomVacuna, cantDosisNumero, inmunidadMesesNumero, dosisSeparacionNumero, labs, enfs);
				this.setVacunaAgregada("block");
				this.setVacunaYaExiste("none");
				this.setCompletarCampos("none");
			} else if (!cantDosis.equals("") && !dosisSeparacion.equals("")){
				cantDosisNumero = Integer.valueOf(cantDosis);
				dosisSeparacionNumero = Integer.valueOf(dosisSeparacion);
				vacService.save(nomVacuna, cantDosisNumero, inmunidadMesesNumero, dosisSeparacionNumero, labs, enfs);
				this.setVacunaAgregada("block");
				this.setVacunaYaExiste("none");
				this.setCompletarCampos("none");
			} else {
				this.setCompletarCampos("block");
				this.setVacunaAgregada("none");
				this.setVacunaYaExiste("none");
			}
		}
		this.setNomVacuna("");
		this.setInmunidadMeses("");
		this.setDosisSeparacion(dosisSeparacion);
		this.setCantDosis("");
	}
	public void eliminarVacuna(String nom) {
		if (!vacService.findByNombreVacuna(nom).isEmpty()) {
			vacService.eliminar(nom);
			this.setVacunaEliminada("block");
			this.setVacunaNoEliminada("none");
		} else {
			this.setVacunaEliminada("none");
			this.setVacunaNoEliminada("block");

		}
	}
	
	public List<Vacuna> getVacs() {
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
	}
	public String getVacunaYaExiste() {
		return vacunaYaExiste;
	}
	public void setVacunaYaExiste(String vacunaYaExiste) {
		this.vacunaYaExiste = vacunaYaExiste;
	}
	public String getVacunaAgregada() {
		return vacunaAgregada;
	}
	public void setVacunaAgregada(String vacunaAgregada) {
		this.vacunaAgregada = vacunaAgregada;
	}
	public String getVacunaEliminada() {
		return vacunaEliminada;
	}
	public void setVacunaEliminada(String vacunaEliminada) {
		this.vacunaEliminada = vacunaEliminada;
	}
	public String getVacunaNoEliminada() {
		return vacunaNoEliminada;
	}
	public void setVacunaNoEliminada(String vacunaNoEliminada) {
		this.vacunaNoEliminada = vacunaNoEliminada;
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
	public String hayVacunas() {
		String res;
		List<Vacuna> vacs = (List<Vacuna>) vacService.find();
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
	public String getCompletarCampos() {
		return completarCampos;
	}
	public void setCompletarCampos(String completarCampos) {
		this.completarCampos = completarCampos;
	}
}
