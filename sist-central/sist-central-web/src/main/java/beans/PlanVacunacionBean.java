package beans;

import datos.entidades.Enfermedad;
import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named("PlanVacunacionBean")
@RequestScoped
public class PlanVacunacionBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String enfermedad;
	private String planVacunacionYaExiste = "none";
	private String elegirEnfermedad = "none";
	private String fechaIncorrecta = "none";
	private String planVacunacionAgregado = "none";
	private String planVacunacionEliminado = "none";
	private String planVacunacionNoEliminado = "none";
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	private String nomPlanVacunacion = null;
	private Date fechaInicio = null;
	private Date fechaFin = null;
	
	@EJB
	logica.servicios.local.PlanVacunacionServiceLocal planVacService;
	@EJB
	logica.servicios.local.EnfermedadServiceLocal enfService;
	
	public void agregarPlanVacunacion() {
		if (!planVacService.find(nomPlanVacunacion).isEmpty()) {
			this.setPlanVacunacionYaExiste("block");
			this.setPlanVacunacionAgregado("none");
			this.setElegirEnfermedad("none");
			this.setFechaIncorrecta("none");
		} else {
			if (enfermedad == null || enfermedad.equals("")){
				this.setElegirEnfermedad("block");
				this.setPlanVacunacionAgregado("none");
				this.setPlanVacunacionYaExiste("none");
				this.setFechaIncorrecta("none");
			} else if (fechaInicio.after(fechaFin)){
				this.setFechaIncorrecta("block");
				this.setElegirEnfermedad("none");
				this.setPlanVacunacionAgregado("none");
				this.setPlanVacunacionYaExiste("none");
			} else {
				Enfermedad enf = enfService.findByNombreEnfermedad(enfermedad).get(0);
				LocalDate localInicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate localFin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				planVacService.save(nomPlanVacunacion, localInicio, localFin, enf);
				this.setFechaIncorrecta("none");
				this.setElegirEnfermedad("none");
				this.setPlanVacunacionAgregado("block");
				this.setPlanVacunacionYaExiste("none");
			}
		}
		this.setNomPlanVacunacion("");
		this.setEnfermedad("");
		this.setFechaFin(null);
		this.setFechaInicio(null);
	}
	
	public List<PlanVacunacion> getPlanes() {
		List<PlanVacunacion> res = new ArrayList<>();
		List<PlanVacunacion> planes = (List<PlanVacunacion>) planVacService.find();
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (PlanVacunacion plan : planes) {
				Matcher match = pattern.matcher(plan.getNombre());
				boolean matchNombre = match.find();
				if (matchNombre) {
					res.add(plan);
				}
				else {
					match = pattern.matcher(plan.getEnfermedad().getNombre());
					boolean matchEnfermedad =  match.find();
					if(matchEnfermedad) {
						res.add(plan);
					}
				}
			}
		} else {
			res = planes;
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
	public String hayPlanesVacunacion() {
		String res;
		List<PlanVacunacion> vacs = (List<PlanVacunacion>) planVacService.find();
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
	/*public List<String> getNombresVacunas() {
		List<Vacuna> vacs = getVacs();
		List<String> nombreVacs = new ArrayList<>();
		for (Vacuna vac:vacs) {
			nombreVacs.add(vac.getNombre());
		}
		return nombreVacs;
	}*/
	public String getElegirEnfermedad() {
		return elegirEnfermedad;
	}
	public void setElegirEnfermedad(String elegirEnfermedad) {
		this.elegirEnfermedad = elegirEnfermedad;
	}

	public String getPlanVacunacionYaExiste() {
		return planVacunacionYaExiste;
	}
	public void setPlanVacunacionYaExiste(String planVacunacionYaExiste) {
		this.planVacunacionYaExiste = planVacunacionYaExiste;
	}
	public String getPlanVacunacionAgregado() {
		return planVacunacionAgregado;
	}
	public void setPlanVacunacionAgregado(String planVacunacionAgregado) {
		this.planVacunacionAgregado = planVacunacionAgregado;
	}
	public String getPlanVacunacionEliminado() {
		return planVacunacionEliminado;
	}
	public void setPlanVacunacionEliminado(String planVacunacionEliminado) {
		this.planVacunacionEliminado = planVacunacionEliminado;
	}
	public String getPlanVacunacionNoEliminado() {
		return planVacunacionNoEliminado;
	}
	public void setPlanVacunacionNoEliminado(String planVacunacionNoEliminado) {
		this.planVacunacionNoEliminado = planVacunacionNoEliminado;
	}
	public String getNomPlanVacunacion() {
		return nomPlanVacunacion;
	}
	public void setNomPlanVacunacion(String nomPlanVacunacion) {
		this.nomPlanVacunacion = nomPlanVacunacion;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getEnfermedad() {
		return enfermedad;
	}
	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}

	public String getFechaIncorrecta() {
		return fechaIncorrecta;
	}

	public void setFechaIncorrecta(String fechaIncorrecta) {
		this.fechaIncorrecta = fechaIncorrecta;
	}
	
	public LocalDate getInicio(String nombre) {
		return planVacService.find(nombre).get().getInicio();
	}
	
	public LocalDate getFin(String nombre) {
		return planVacService.find(nombre).get().getFin();
	}
	
	public String getEnfermedad(String nombre) {
		return planVacService.find(nombre).get().getEnfermedad().getNombre();
	}
	
	public List<Etapa> getEtapas(String nombre) {
		return planVacService.find(nombre).get().getEtapas();
	}
	
	public Date getFechaFin(String nombre) {
		Optional<PlanVacunacion> pvac = planVacService.find(nombre);
		LocalDate fechaFin = pvac.get().getFin();
		Date res = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return res;
	}

	//devuelve 0 si es acutal, 1 si es futuro y -1 si es pasado
	public int esActual(LocalDate inicio, LocalDate fin) {
		if(inicio.isAfter(LocalDate.now())) {
			return 1;
		}
		else {
			if(fin.isBefore(LocalDate.now())) {
				return -1;
			}
			else {
				return 0;
			}
		}
	}
	
	
}
