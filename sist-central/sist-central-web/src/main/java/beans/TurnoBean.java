package beans;

import java.io.Serializable;
import java.time.LocalTime;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import javax.inject.Named;

@Named("TurnoBean")
@RequestScoped
public class TurnoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String vacunatorio;
	private String nomTurno;
	private String horaInicio;
	private String horaFin;
	private String minInicio;
	private String minFin;
	private String turnoYaExiste = "none";
	private String turnoAgregado = "none";
	private String turnoEliminado = "none";
	private String turnoNoEliminado = "none";
	private String horaIncorrecta = "none";
	private String hayBusqueda = "none";
	private String color = "white";
	private String colorSecundario = "#222938";
	private String busqueda = null;
	private Boolean realizarBusqueda = false;
	
	@EJB
	logica.servicios.local.TurnoServiceLocal turnoService;
	
	public void agregarTurno(String vac) {
		if (!turnoService.find(vac, nomTurno).isEmpty()) {
			this.setTurnoYaExiste("block");
			this.setTurnoAgregado("none");
			this.setHoraIncorrecta("none");
		} else if (horaInicio.equals("Hora") || horaFin.equals("Hora") || minInicio.equals("Minuto") || minFin.equals("Minuto")){
			this.setHoraIncorrecta("block");
			this.setTurnoYaExiste("none");
			this.setTurnoAgregado("none");
		} else {
			String horarioInicioString = horaInicio + ":" + minInicio;
			String horarioFinString = horaFin + ":" + minFin;
			LocalTime horarioInicio = LocalTime.parse(horarioInicioString);
			LocalTime horarioFin = LocalTime.parse(horarioFinString);
			turnoService.addTurno(nomTurno, vac, horarioInicio, horarioFin);
			this.setTurnoYaExiste("none");
			this.setTurnoAgregado("block");
			this.setHoraIncorrecta("none");
		}
		this.setNomTurno("");
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

	public String getNomTurno() {
		return nomTurno;
	}

	public void setNomTurno(String nomTurno) {
		this.nomTurno = nomTurno;
	}

	public String getTurnoYaExiste() {
		return turnoYaExiste;
	}

	public void setTurnoYaExiste(String turnoYaExiste) {
		this.turnoYaExiste = turnoYaExiste;
	}

	public String getTurnoAgregado() {
		return turnoAgregado;
	}

	public void setTurnoAgregado(String turnoAgregado) {
		this.turnoAgregado = turnoAgregado;
	}

	public String getTurnoEliminado() {
		return turnoEliminado;
	}

	public void setTurnoEliminado(String turnoEliminado) {
		this.turnoEliminado = turnoEliminado;
	}

	public String getTurnoNoEliminado() {
		return turnoNoEliminado;
	}

	public void setTurnoNoEliminado(String turnoNoEliminado) {
		this.turnoNoEliminado = turnoNoEliminado;
	}

	public String getHoraIncorrecta() {
		return horaIncorrecta;
	}

	public void setHoraIncorrecta(String horaIncorrecta) {
		this.horaIncorrecta = horaIncorrecta;
	}
	
	public String getHoraInicio() {
		return this.horaInicio;
	}
	
	public String getHoraFin() {
		return this.horaFin;
	}
	
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	
	public String getMinInicio() {
		return this.minInicio;
	}
	
	public String getMinFin() {
		return this.minFin;
	}
	
	public void setMinInicio(String minInicio) {
		this.minInicio = minInicio;
	}
	
	public void setMinFin(String minFin) {
		this.minFin = minFin;
	}
	
}
