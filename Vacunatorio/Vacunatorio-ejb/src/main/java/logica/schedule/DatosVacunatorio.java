package logica.schedule;

import java.io.Serializable;
import java.util.Date;


import datos.dtos.VacunatorioDTO;

public class DatosVacunatorio implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String fechaEnvio;
	private VacunatorioDTO vac;
	
	public DatosVacunatorio(String fecha, VacunatorioDTO vac) {
		this.fechaEnvio = fecha;
		this.setVac(vac);
	}
	
	public DatosVacunatorio() {
	}
	
	public String getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public VacunatorioDTO getVac() {
		return vac;
	}

	public void setVac(VacunatorioDTO vac) {
		this.vac = vac;
	}

}
