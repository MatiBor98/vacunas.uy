package datos.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class AsignacionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")  
	private Date fechaInicio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")  
	private Date fechaFin;
	private PuestoVacunacionDTO puestoVacunacion;
	private VacunadorDTO vacunador;
	private TurnoDTO turno;

	public AsignacionDTO() {}
	
	public AsignacionDTO(VacunadorDTO vac, TurnoDTO turn, PuestoVacunacionDTO pVac, Date fechaI, Date fechaF) {
		this.fechaFin = fechaF;
		this.fechaInicio = fechaI;
		this.puestoVacunacion = pVac;
		this.turno = turn;
		this.vacunador = vac;
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

	public PuestoVacunacionDTO getPuestoVacunacion() {
		return puestoVacunacion;
	}

	public void setPuestoVacunacion(PuestoVacunacionDTO puestoVacunacion) {
		this.puestoVacunacion = puestoVacunacion;
	}

	public TurnoDTO getTurno() {
		return turno;
	}

	public void setTurno(TurnoDTO turno) {
		this.turno = turno;
	}

	public VacunadorDTO getVacunador() {
		return vacunador;
	}

	public void setVacunador(VacunadorDTO vacunador) {
		this.vacunador = vacunador;
	}
}

