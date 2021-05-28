package beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import javax.inject.Named;

import datos.entidades.Trabajos;


@Named("EtapaBean")
@RequestScoped
public class EtapaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String[] trabajos;
	private String vacuna;
	private String elegirVacuna = "none";
	private String elegirFecha = "none";
	private String fechaIncorrecta = "none";
	private String fechaFinIncorrecta= "none";
	private String etapaAgregada = "none";
	private String antesDeHoy = "none";
	private String edadesIncorrectas = "none";
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private String edadMinima;
	private String edadMaxima;

	@EJB
	logica.servicios.local.PlanVacunacionServiceLocal pVacService;
	@EJB
	logica.servicios.local.EtapaController etapaService;
	
	public void agregarEtapa(String nomPlan) {
		if (vacuna == null || vacuna.equals("")) {
			this.setElegirVacuna("block");
			this.setElegirFecha("none");
			this.setFechaIncorrecta("none");
			this.setAntesDeHoy("none");
			this.setFechaFinIncorrecta("none");
			this.setEdadesIncorrectas("none");
			this.setEtapaAgregada("none");
		} else if (fechaInicio == null || fechaFin == null) {
			this.setElegirFecha("block");
			this.setElegirVacuna("none");
			this.setFechaIncorrecta("none");
			this.setAntesDeHoy("none");
			this.setFechaFinIncorrecta("none");
			this.setEdadesIncorrectas("none");
			this.setEtapaAgregada("none");
		} else if (Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()).after(fechaInicio)) {
			this.setAntesDeHoy("block");
			this.setElegirVacuna("none");
			this.setFechaIncorrecta("none");
			this.setElegirFecha("none");
			this.setFechaFinIncorrecta("none");
			this.setEdadesIncorrectas("none");
			this.setEtapaAgregada("none");
		} else if (fechaInicio.after(fechaFin)) {
			this.setFechaIncorrecta("block");
			this.setElegirVacuna("none");
			this.setElegirFecha("none");
			this.setAntesDeHoy("none");
			this.setFechaFinIncorrecta("none");
			this.setEdadesIncorrectas("none");
			this.setEtapaAgregada("none");
		} else if (fechaFin.after(pVacService.getFechaFin(ConsultaBean.getConsultaPlanVacunacionStatic()))) {
			this.setFechaFinIncorrecta("block");
			this.setElegirVacuna("none");
			this.setElegirFecha("none");
			this.setAntesDeHoy("none");
			this.setFechaIncorrecta("none");
			this.setEdadesIncorrectas("none");
			this.setEtapaAgregada("none");
		} else if (!(edadMinima.equals("")) && !(edadMaxima.equals(""))) {
			int edadMin = Integer.valueOf(edadMinima);
			int edadMax = Integer.valueOf(edadMaxima);
			if (edadMin > edadMax) {
				this.setEdadesIncorrectas("block");
				this.setFechaFinIncorrecta("none");
				this.setElegirVacuna("none");
				this.setElegirFecha("none");
				this.setAntesDeHoy("none");
				this.setFechaIncorrecta("none");
				this.setEtapaAgregada("none");
			} else {
				LocalDate localInicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate localFin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				List<Trabajos> trabajosEnum = new ArrayList<>();
				for(String trabajo:trabajos) {
					Trabajos trab = Trabajos.valueOf(trabajo);
					trabajosEnum.add(trab);
				}
				etapaService.save(vacuna, localInicio, localFin, nomPlan, descripcion, trabajosEnum, edadMin, edadMax);
				this.setEtapaAgregada("block");
				this.setEdadesIncorrectas("none");
				this.setFechaFinIncorrecta("none");
				this.setElegirVacuna("none");
				this.setElegirFecha("none");
				this.setAntesDeHoy("none");
				this.setFechaIncorrecta("none");
			}
		} else {
			int edadMin;
			int edadMax;
			if (edadMinima.equals("")) {
				edadMin = -1;
			} else {
				edadMin = Integer.valueOf(edadMinima);
			}
			if (edadMaxima.equals("")) {
				edadMax = -1;
			} else {
				edadMax = Integer.valueOf(edadMaxima);
			}
			LocalDate localInicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localFin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			List<Trabajos> trabajosEnum = new ArrayList<>();
			for(String trabajo:trabajos) {
				Trabajos trab = Trabajos.valueOf(trabajo);
				trabajosEnum.add(trab);
			}
			etapaService.save(vacuna, localInicio, localFin, nomPlan, descripcion, trabajosEnum, edadMin, edadMax);
			this.setEtapaAgregada("block");
			this.setEdadesIncorrectas("none");
			this.setFechaFinIncorrecta("none");
			this.setElegirVacuna("none");
			this.setElegirFecha("none");
			this.setAntesDeHoy("none");
			this.setFechaIncorrecta("none");
		}
		this.setVacuna(null);
		this.setDescripcion(null);
		this.setFechaInicio(null);
		this.setFechaFin(null);
		this.setTrabajos(null);
		this.setEdadMaxima("");
		this.setEdadMinima("");
			
	}
	public String[] getTrabajos() {
		return trabajos;
	}
	public void setTrabajos(String[] trabajos) {
		this.trabajos = trabajos;
	}
	public String getVacuna() {
		return vacuna;
	}
	public void setVacuna(String vacuna) {
		this.vacuna = vacuna;
	}
	public String getElegirVacuna() {
		return elegirVacuna;
	}
	public void setElegirVacuna(String elegirVacuna) {
		this.elegirVacuna = elegirVacuna;
	}
	public String getEtapaAgregada() {
		return etapaAgregada;
	}
	public void setEtapaAgregada(String etapaAgregada) {
		this.etapaAgregada = etapaAgregada;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	public String getEdadMinima() {
		return edadMinima;
	}
	public void setEdadMinima(String edadMinima) {
		this.edadMinima = edadMinima;
	}
	public String getEdadMaxima() {
		return edadMaxima;
	}
	public void setEdadMaxima(String edadaxima) {
		this.edadMaxima = edadaxima;
	}
	public String getElegirFecha() {
		return elegirFecha;
	}
	public void setElegirFecha(String elegirFecha) {
		this.elegirFecha = elegirFecha;
	}
	public String getFechaIncorrecta() {
		return fechaIncorrecta;
	}
	public void setFechaIncorrecta(String elegirFecha) {
		this.fechaIncorrecta = elegirFecha;
	}
	public String getAntesDeHoy() {
		return antesDeHoy;
	}
	public void setAntesDeHoy(String elegirFecha) {
		this.antesDeHoy = elegirFecha;
	}
	public String getFechaFinIncorrecta() {
		return fechaFinIncorrecta;
	}
	public void setFechaFinIncorrecta(String elegirFecha) {
		this.fechaFinIncorrecta = elegirFecha;
	}
	public String getEdadesIncorrectas() {
		return edadesIncorrectas;
	}
	public void setEdadesIncorrectas(String elegirFecha) {
		this.edadesIncorrectas = elegirFecha;
	}
	public List<String> getNombresTrabajos() {
		return etapaService.getNombresTrabajos();
	}
	
	
}
