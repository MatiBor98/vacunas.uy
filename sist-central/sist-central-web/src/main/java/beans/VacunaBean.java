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

@Named("VacunaBean")
@RequestScoped
public class VacunaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String[] enfermedades;
	private String[] laboratorios;
	private String vacunaYaExiste = "none";
	private String vacunaModificada = "none";
	private String elegirLaboratorio = "none";
	private String elegirEnfermedad = "none";
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
	/*private Map<String, String> params =FacesContext.getCurrentInstance().
            getExternalContext().getRequestParameterMap();
	private String vacModificar = params.get("vacuna");
	private String vacModificar = null;*/
	
	@EJB
	logica.servicios.local.VacunaServiceLocal vacService;
	@EJB
	logica.servicios.local.LaboratorioServiceLocal labService;
	@EJB
	logica.servicios.local.EnfermedadServiceLocal enfService;
	
	
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
			for (String lab: laboratorios) {
				labs.add(labService.findByNombreLaboratorio(lab).get(0));
			}
			List<Enfermedad> enfs = new ArrayList<>();
			for (String enf: enfermedades) {
				enfs.add(enfService.findByNombreEnfermedad(enf).get(0));
			}
			if (labs.isEmpty()) {
				this.setElegirLaboratorio("block");
				this.setVacunaAgregada("none");
				this.setVacunaYaExiste("none");
				this.setCompletarCampos("none");
				this.setElegirEnfermedad("none");
				//this.setVacModificar2(vacModificar);
			} else if (enfs.isEmpty()){
				this.setElegirEnfermedad("block");
				this.setElegirLaboratorio("none");
				this.setVacunaAgregada("none");
				this.setVacunaYaExiste("none");
				this.setCompletarCampos("none");
				//this.setVacModificar2(vacModificar);
				} else {
					int inmunidadMesesNumero = Integer.valueOf(inmunidadMeses);		
					if (cantDosis.equals("") && dosisSeparacion.equals("")) {
						cantDosisNumero = 1;
						dosisSeparacionNumero = 0;
						vacService.save(nomVacuna, cantDosisNumero, inmunidadMesesNumero, dosisSeparacionNumero, labs, enfs);
						this.setVacunaAgregada("block");
						this.setVacunaYaExiste("none");
						this.setCompletarCampos("none");
						this.setElegirLaboratorio("none");
						this.setElegirEnfermedad("none");
						//this.setVacModificar2(vacModificar);
					} else if (!cantDosis.equals("") && !dosisSeparacion.equals("")){
						cantDosisNumero = Integer.valueOf(cantDosis);
						dosisSeparacionNumero = Integer.valueOf(dosisSeparacion);
						vacService.save(nomVacuna, cantDosisNumero, inmunidadMesesNumero, dosisSeparacionNumero, labs, enfs);
						this.setVacunaAgregada("block");
						this.setVacunaYaExiste("none");
						this.setCompletarCampos("none");
						this.setElegirLaboratorio("none");
						this.setElegirEnfermedad("none");
						//this.setVacModificar2(vacModificar);
					} else {
						this.setCompletarCampos("block");
						this.setVacunaAgregada("none");
						this.setVacunaYaExiste("none");
						this.setElegirLaboratorio("none");
						this.setElegirEnfermedad("none");
						//this.setVacModificar2(vacModificar);
					}
				}
		}
		this.setNomVacuna("");
		this.setInmunidadMeses("");
		this.setDosisSeparacion("");
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
	public String[] getEnfermedades() {
		return enfermedades;
	}
	public void setEnfermedades(String[] enfermedades) {
		this.enfermedades = enfermedades;
	}
	public String[] getLaboratorios() {
		return laboratorios;
	}
	public void setLaboratorios(String[] laboratorios) {
		this.laboratorios = laboratorios;
	}
	public String getElegirLaboratorio() {
		return elegirLaboratorio;
	}
	public void setElegirLaboratorio(String elegirLaboratorio) {
		this.elegirLaboratorio = elegirLaboratorio;
	}
	
	public List<String> getNombresVacunas() {
		List<Vacuna> vacs = getVacs();
		List<String> nombreVacs = new ArrayList<>();
		for (Vacuna vac:vacs) {
			nombreVacs.add(vac.getNombre());
		}
		return nombreVacs;
	}
	public String getElegirEnfermedad() {
		return elegirEnfermedad;
	}
	public void setElegirEnfermedad(String elegirEnfermedad) {
		this.elegirEnfermedad = elegirEnfermedad;
	}
	
	public void modificarVacuna(String nombre) {
		int cantDosisNumero;
		int dosisSeparacionNumero;
		List<Laboratorio> labs = new ArrayList<>();
		for (String lab: laboratorios) {
			labs.add(labService.findByNombreLaboratorio(lab).get(0));
		}
		List<Enfermedad> enfs = new ArrayList<>();
		for (String enf: enfermedades) {
			enfs.add(enfService.findByNombreEnfermedad(enf).get(0));
		}
		if (labs.isEmpty()) {
			this.setElegirLaboratorio("block");
			this.setVacunaAgregada("none");
			this.setVacunaYaExiste("none");
			this.setCompletarCampos("none");
			this.setElegirEnfermedad("none");
			//this.setVacModificar(vacModificar);
		} else if (enfs.isEmpty()){
			this.setElegirEnfermedad("block");
			this.setElegirLaboratorio("none");
			this.setVacunaAgregada("none");
			this.setVacunaYaExiste("none");
			this.setCompletarCampos("none");
			} else {
				int inmunidadMesesNumero = Integer.valueOf(inmunidadMeses);		
				if (cantDosis.equals("") && dosisSeparacion.equals("")) {
					cantDosisNumero = 1;
					dosisSeparacionNumero = 0;
					vacService.modificarVacuna(/*this.vacModificar*/nombre, cantDosisNumero, inmunidadMesesNumero, dosisSeparacionNumero, labs, enfs);
					this.setVacunaModificada("block");
					this.setVacunaAgregada("none");
					this.setVacunaYaExiste("none");
					this.setCompletarCampos("none");
					this.setElegirLaboratorio("none");
					this.setElegirEnfermedad("none");
				} else if (!cantDosis.equals("") && !dosisSeparacion.equals("")){
					cantDosisNumero = Integer.valueOf(cantDosis);
					dosisSeparacionNumero = Integer.valueOf(dosisSeparacion);
					vacService.modificarVacuna(/*this.vacModificar*/nombre, cantDosisNumero, inmunidadMesesNumero, dosisSeparacionNumero, labs, enfs);
					this.setVacunaModificada("block");
					this.setVacunaAgregada("none");
					this.setVacunaYaExiste("none");
					this.setCompletarCampos("none");
					this.setElegirLaboratorio("none");
					this.setElegirEnfermedad("none");
				} else {
					this.setCompletarCampos("block");
					this.setVacunaModificada("none");
					this.setVacunaAgregada("none");
					this.setVacunaYaExiste("none");
					this.setElegirLaboratorio("none");
					this.setElegirEnfermedad("none");
				}
			}
			this.setNomVacuna("");
			this.setInmunidadMeses("");
			this.setDosisSeparacion("");
			this.setCantDosis("");
	}
	
	
	/*public String getVacModificar() {
		return vacModificar;
	}
	public void setVacModificar(String vacModificar) {
		this.vacModificar = vacModificar;
	}*/
	public String getVacunaModificada() {
		return vacunaModificada;
	}
	public void setVacunaModificada(String vacunaModificada) {
		this.vacunaModificada = vacunaModificada;
	}
	/*public String getVacModificar2() {
		return vacModificar2;
	}
	public void setVacModificar2(String vacModificar2) {
		this.vacModificar2 = vacModificar2;
	}*/
	
	
}
