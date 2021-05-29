package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import datos.dtos.CiudadanoDTO;
import datos.exceptions.CiudadanoNoEncontradoException;
import logica.servicios.local.CiudadanoServiceLocal;

@Named
@RequestScoped
public class ConsultaUsuarioFrontOfficeBean implements Serializable {

	@EJB
	CiudadanoServiceLocal usuarios;
	
	private static final long serialVersionUID = 1L;
	
	private int consultaUsuario;
	private static int consultaUsuarioStatic;
	
	private String email;
	private String nombre;
	private boolean vacunador;
	private String busqueda;
	private boolean realizarBusqueda;
	private String hayBusqueda="none";
	private String hayVacunadores = "block";
	private String hayCiudadanos = "block";
	private String color = "white";
	private String colorSecundario = "#222938";
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isVacunador() {
		return vacunador;
	}

	public void setVacunador(boolean vacunador) {
		this.vacunador = vacunador;
	}

	public int getConsultaUsuario() {
		return consultaUsuario;
	}
	
	public static int getConsultaUsuarioStatic() {
		return consultaUsuarioStatic;
	}
	
	public void overwriteCiudadano(int CI, String nombre, String email, boolean esVac){
		CiudadanoDTO ciudadanoDTO = new CiudadanoDTO(CI, nombre, email, esVac);
		usuarios.overwriteCiudadano(ciudadanoDTO);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Ciudadano modificado con exito"));
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
	
	public List<CiudadanoDTO> getUsuarios() {
		List<CiudadanoDTO> res = new ArrayList<>();
		List<CiudadanoDTO> usus = (List<CiudadanoDTO>) usuarios.find();
		if (this.realizarBusqueda) {
			Pattern pattern = Pattern.compile(this.busqueda.trim(), Pattern.CASE_INSENSITIVE);
			for (CiudadanoDTO usu : usus) {
				Matcher match1 = pattern.matcher(usu.getNombre());
				boolean matchNombre = match1.find();
				Matcher match2 = pattern.matcher(String.valueOf(usu.getCi()));
				boolean matchCI = match2.find();
				if (matchNombre || matchCI) {
					res.add(usu);
				}
			}
		} else {
			res = usus;
		}
		return res;
	}

	public String getHayVacunadores() {
		return hayVacunadores;
	}

	public void setHayVacunadores(String hayVacunadores) {
		this.hayVacunadores = hayVacunadores;
	}

	public String getHayCiudadanos() {
		return hayCiudadanos;
	}

	public void setHayCiudadanos(String hayCiudadanos) {
		this.hayCiudadanos = hayCiudadanos;
	}
	
	public String hayVacunadores() {
		List<CiudadanoDTO> usus = getUsuarios();
		boolean hayVac = false;
		for (CiudadanoDTO usu:usus) {
			if (!hayVac && usu.getVacunador()) {
				hayVac = true;
				this.setHayVacunadores("none");
				break;
			}
		}
		if (!hayVac) {
			this.setHayVacunadores("block");
		}
		return this.hayVacunadores;
	}
	
	public String hayCiudadanos() {
		List<CiudadanoDTO> usus = getUsuarios();
		boolean hayCiud = false;
		for (CiudadanoDTO usu:usus) {
			if (!hayCiud && !usu.getVacunador()) {
				hayCiud = true;
				this.setHayCiudadanos("none");
				break;
			}
		}
		if (!hayCiud) {
			this.setHayCiudadanos("block");
		}
		return this.hayCiudadanos;
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
}
