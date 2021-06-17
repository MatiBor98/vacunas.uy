package datos.dtos;


import java.io.Serializable;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class IntervaloDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fechayHora;
	private AgendaDTO2 agenda2;
	private AgendaDTO agenda;
	private List<ReservaDTO> reservas;
	private AtomicInteger cantidadReservas = new AtomicInteger(0);

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

	public List<ReservaDTO> getReservas() {
		return new LinkedList<>(reservas);
	}
	public AgendaDTO2 getAgenda2() {
		return agenda2;
	}
	public void setAgenda2(AgendaDTO2 agenda2) {
		this.agenda2 = agenda2;
	}

	public AgendaDTO getAgenda() {
		return agenda;
	}

	public void setAgenda(AgendaDTO agenda) {
		this.agenda = agenda;
	}

	public AtomicInteger getCantidadReservas() {
		return cantidadReservas;
	}

	public void setCantidadReservas(AtomicInteger cantidadReservas) {
		this.cantidadReservas = cantidadReservas;
	}

}