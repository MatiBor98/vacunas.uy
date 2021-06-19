package logica.schedule;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import datos.dtos.ReservaDTO;
import datos.dtos.VacunatorioDTO;

public class DatosVacunatorio implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String fechaEnvio;
	private VacunatorioDTO vac;
	private List<ReservaDTO> reservas;
	
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
	public List<ReservaDTO> getReservas() {
		return reservas;
	}

	public void setReservas(List<ReservaDTO> reservas) {
		this.reservas = reservas;
	}
}
