package datos.dtos;


import java.io.Serializable;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IntervaloDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fechayHora;
	private AgendaDTO agenda;
	private AgendaDTO2 agenda2;
	private List<ReservaDTO> reservas;
	private AtomicInteger cantidadReservas = new AtomicInteger(0);

	public IntervaloDTO(String fechayHora, AgendaDTO agenda) {
		this.fechayHora = fechayHora;
		this.agenda = agenda;
		this.reservas = new LinkedList<>();
	}
	
	public IntervaloDTO(String fechayHora, AgendaDTO2 agenda) {
		this.fechayHora = fechayHora;
		this.agenda2 = agenda;
		this.reservas = new LinkedList<>();
	}
	
	public IntervaloDTO() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getFechayHora() {
		return fechayHora;
	}

	public void setFechayHora(String fechayHora) {
		this.fechayHora = fechayHora;
	}

	public AgendaDTO getAgenda() {
		return agenda;
	}

	public void setAgenda(AgendaDTO agenda) {
		this.agenda = agenda;
	}

	public List<ReservaDTO> getReservas() {
		return new LinkedList<>(reservas);
	}
	public AtomicInteger getCantidadReservas() {
		return cantidadReservas;
	}
	public AgendaDTO2 getAgenda2() {
		return agenda2;
	}
	public void setAgenda2(AgendaDTO2 agenda2) {
		this.agenda2 = agenda2;
	}

}