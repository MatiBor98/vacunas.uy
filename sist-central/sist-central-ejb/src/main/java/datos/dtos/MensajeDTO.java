package datos.dtos;

import java.io.Serializable;

public class MensajeDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fechaHora,texto,vacunadorNombre;
	private int vacunadorCi;
	
	public MensajeDTO() {
	}

	public String getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getVacunadorNombre() {
		return vacunadorNombre;
	}

	public void setVacunadorNombre(String vacunadorNombre) {
		this.vacunadorNombre = vacunadorNombre;
	}

	public int getVacunadorCi() {
		return vacunadorCi;
	}

	public void setVacunadorCi(int vacunadorCi) {
		this.vacunadorCi = vacunadorCi;
	}

	
	
}
