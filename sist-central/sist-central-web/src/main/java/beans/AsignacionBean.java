package beans;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;

import com.auth0.jwt.interfaces.Claim;

import Utilities.TokenVerifier;
import datos.dtos.CiudadanoDTO;
import datos.entidades.Asignacion;
import datos.entidades.Laboratorio;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Turno;
import datos.entidades.Vacunador;
import datos.exceptions.CiudadanoNoEncontradoException;
import datos.exceptions.CiudadanoRegistradoException;
import plataformainteroperabilidad.Ciudadano;
import plataformainteroperabilidad.Ciudadanos;
import plataformainteroperabilidad.CiudadanosService;
import javax.faces.view.ViewScoped;


@Named("AsignacionBean")
@ViewScoped
public class AsignacionBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String vacunador;
	private String turno;
	private String elegirVacunador = "none";
	private String elegirTurno = "none";
	private String elegirFecha = "none";
	private String fechaIncorrecta = "none";
	private String asignacionAgregada = "none";
	private String antesDeHoy = "none";
	private Date fechaInicio;
	private Date fechaFin;
	//variables de la consulta frontoffice
	private String cid;
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;

	@EJB
	logica.servicios.local.PuestoVacunacionBeanLocal pVacService;
	@EJB
	logica.servicios.local.CiudadanoServiceLocal ciudadanoService;
	@EJB
	logica.servicios.local.TurnoServiceLocal turnoService;
	
	
	@PostConstruct
    public void init() {
        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get("JWT");
        if(cookie!= null) {

            TokenVerifier tokenVerifier = new TokenVerifier();
            String jwtIdToken = tokenVerifier.getAtributeFromJWTString(cookie.getValue(),"id_token");
            Map<String, Claim> claimMap = tokenVerifier.performActionWithFreshToken(jwtIdToken, FacesContext.getCurrentInstance().getExternalContext());
            cid = claimMap.get("numero_documento").asString();

        } else {
            cid = null;
        }

    }
	
	public void agregarAsignacion(String  nombreVacunatorio, String nombrePuesto) {
		if (vacunador == null || vacunador.equals("")) {
			this.setElegirVacunador("block");
			this.setElegirFecha("none");
			this.setFechaIncorrecta("none");
			this.setAntesDeHoy("none");
			this.setAsignacionAgregada("none");
			this.setElegirTurno("none");
		} else if (turno == null || turno.equals("")) {
			this.setElegirVacunador("none");
			this.setElegirFecha("none");
			this.setFechaIncorrecta("none");
			this.setAntesDeHoy("none");
			this.setAsignacionAgregada("none");
			this.setElegirTurno("block");
		} else if (fechaInicio == null || fechaFin == null) {
			this.setElegirFecha("block");
			this.setElegirVacunador("none");
			this.setFechaIncorrecta("none");
			this.setAntesDeHoy("none");
			this.setAsignacionAgregada("none");
			this.setElegirTurno("none");
		} else if (Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()).after(fechaInicio)) {
			this.setAntesDeHoy("block");
			this.setElegirVacunador("none");
			this.setFechaIncorrecta("none");
			this.setElegirFecha("none");
			this.setAsignacionAgregada("none");
			this.setElegirTurno("none");
		} else if (fechaInicio.after(fechaFin)) {
			this.setFechaIncorrecta("block");
			this.setElegirVacunador("none");
			this.setElegirFecha("none");
			this.setAntesDeHoy("none");
			this.setAsignacionAgregada("none");
			this.setElegirTurno("none");
		} else {
			LocalDate localInicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localFin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			String CIString = StringUtils.substringBefore(vacunador, " -");
			int ciVac = Integer.valueOf(CIString);
			Vacunador vac = ciudadanoService.findVacunador(ciVac);
			Turno turn = turnoService.find(nombreVacunatorio, turno).get(0);
			PuestoVacunacion pVac = pVacService.find(nombreVacunatorio, nombrePuesto).get(0);
			pVacService.addAsignacion(vac, turn, pVac, localInicio, localFin);
			this.setFechaIncorrecta("none");
			this.setElegirVacunador("none");
			this.setElegirFecha("none");
			this.setAntesDeHoy("none");
			this.setAsignacionAgregada("block");
			this.setElegirTurno("none");
		}
		this.setVacunador(null);
		this.setTurno(null);
		this.setFechaInicio(null);
		this.setFechaFin(null);
			
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
	public String getVacunador() {
		return vacunador;
	}
	public void setVacunador(String vacunador) {
		this.vacunador = vacunador;
	}
	public String getTurno() {
		return this.turno;
	}
	public void setTurno(String vacunador) {
		this.turno = vacunador;
	}
	public String getElegirVacunador() {
		return elegirVacunador;
	}
	public void setElegirVacunador(String elegirVacunador) {
		this.elegirVacunador = elegirVacunador;
	}
	public String getAsignacionAgregada() {
		return asignacionAgregada;
	}
	public void setAsignacionAgregada(String asignacionAgregada) {
		this.asignacionAgregada = asignacionAgregada;
	}
	
	public String getElegirTurno() {
		return this.elegirTurno;
	}
	public void setElegirTurno(String asignacionAgregada) {
		this.elegirTurno = asignacionAgregada;
	}
	
	public List<Vacunador> getVacunadoresNoAsignados(String  nombreVacunatorio, String nombrePuesto) {
		return pVacService.getVacunadoresNoAsignados(nombreVacunatorio, nombrePuesto);
	}
	
	public List<String> getCIyNombreVacunadores(String  nombreVacunatorio, String nombrePuesto) {
		List<Vacunador> vacs = getVacunadoresNoAsignados(nombreVacunatorio, nombrePuesto);
		List<String> res = new ArrayList<>();
		for(Vacunador vac:vacs) {
			String CIyNombre = vac.getCi() + " - " + vac.getNombre();
			res.add(CIyNombre);
		}
		return res;
	}
	public String hayAsignaciones() {
		String res;
		List<Asignacion> asigs = (List<Asignacion>) ciudadanoService.findAsignacionesVacunador(cid);
		if (asigs.isEmpty()) {
			res = "block";
			
		} else {
			res = "none";
		}
		return res;
	}
	
	public List<Asignacion> getAsignaciones() {
		List<Asignacion> res = new ArrayList<>();
		List<Asignacion> asigs = (List<Asignacion>) ciudadanoService.findAsignacionesVacunador(cid);
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (Asignacion asig : asigs) {
				Matcher match = pattern.matcher(asig.getPuestoVacunacion().getVacunatorio().getNombre());
				boolean matchNombre = match.find();
				if (matchNombre) {
					res.add(asig);
				}
			}
		} else {
			res = asigs;
		}
		return res;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
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
	
}
