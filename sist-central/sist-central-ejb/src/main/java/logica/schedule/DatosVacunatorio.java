package logica.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import datos.dtos.VacunatorioDTO;

public class DatosVacunatorio implements Serializable{
	

	private static final long serialVersionUID = 1L;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-M-yyyy hh:mm:ss")  
	private Date fechaEnvio;
	private VacunatorioDTO vac;
	
	public DatosVacunatorio(Date fecha, VacunatorioDTO vac) {
		this.fechaEnvio = fecha;
		this.vac = vac;
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

}
