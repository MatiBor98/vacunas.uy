package datos.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serializable;
import java.util.Date;


public class AsignacionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fechaInicio;
	private String fechaFin;
	private PuestoVacunacionDTO puestoVacunacion;
	private VacunadorDTO vacunador;
	private TurnoDTO turno;

	public AsignacionDTO() {}
	
	public AsignacionDTO(VacunadorDTO vac, TurnoDTO turn, PuestoVacunacionDTO pVac, String fechaI, String fechaF) {
		this.fechaFin = fechaF;
		this.fechaInicio = fechaI;
		this.puestoVacunacion = pVac;
		this.turno = turn;
		this.vacunador = vac;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
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

