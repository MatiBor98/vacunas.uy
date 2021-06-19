package datos.dtos;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class PuestoVacunacionDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nomVacunatorio;

	private String nombrePuesto;
	private List<AsignacionDTO> asignaciones;
	public String getVacunatorio() {
		return nomVacunatorio;
	}

	public void setVacunatorio(String vacunatorio) {
		this.nomVacunatorio = vacunatorio;
	}

	public String getNombrePuesto() {
		return nombrePuesto;
	}

	public void setNombrePuesto(String nombrePuesto) {
		this.nombrePuesto = nombrePuesto;
	}

	public PuestoVacunacionDTO(String vacunatorio, String nombrePuesto) {
		super();
		this.nomVacunatorio = vacunatorio;
		this.nombrePuesto = nombrePuesto;
		this.asignaciones = new ArrayList<AsignacionDTO>();
	}

	public PuestoVacunacionDTO() {}
	public List<AsignacionDTO> getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(List<AsignacionDTO> asignaciones) {
		this.asignaciones = asignaciones;
	}

	public String getNomVacunatorio() {
		return nomVacunatorio;
	}

	public void setNomVacunatorio(String nomVacunatorio) {
		this.nomVacunatorio = nomVacunatorio;
	}
}
