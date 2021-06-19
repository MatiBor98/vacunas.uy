package logica.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import datos.dtos.ReservaDTO;
import datos.dtos.VacunatorioDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DatosVacunatorio implements Serializable{
	

	private static final long serialVersionUID = 1L;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-M-yyyy hh:mm:ss")  
	private Date fechaEnvio;
	private VacunatorioDTO vac;
	private List<ReservaDTO> reservas;
	
	public DatosVacunatorio(Date fecha, VacunatorioDTO vac, List<ReservaDTO> reservas) {
		this.fechaEnvio = fecha;
		this.vac = vac;
		this.reservas = reservas;
	}
	
	public DatosVacunatorio() {
	}
	
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	public VacunatorioDTO getVac() {
		return vac;
	}

	public void setVac(VacunatorioDTO vac) {
		this.vac = vac;
	}

	public List<ReservaDTO> getReservas() {
		return reservas;
	}

	public void setReservas(List<ReservaDTO> reservas) {
		this.reservas = reservas;
	}

}
