package datos.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class MensajeDTO implements Serializable, Comparable<MensajeDTO>{

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

	public String getFechaHoraFormatoLegible() {
		LocalDateTime fechaHoraGMT = LocalDateTime.parse(fechaHora);
		LocalDateTime fechaHoraLocal = fechaHoraGMT.minusHours(3);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		return fechaHoraLocal.format(formatter);
	}

	@Override
	public int compareTo(MensajeDTO other) {
		if (getFechaHora() == null || other.getFechaHora() == null) {
		      return 0;
		    }	
		return getFechaHora().compareTo(other.getFechaHora());
	}
	
}
