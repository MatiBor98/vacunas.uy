package datos.entidades;

import datos.exceptions.IntervaloNoDisponibleException;

import java.io.Serializable;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Intervalo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="intervaloId")
    @SequenceGenerator(name="intervaloId",sequenceName="intervaloId", allocationSize=1)
	private Integer id;

	private LocalDateTime fechayHora;

	@ManyToOne
	@JoinColumn(name="agendaId", nullable=false)
	private Agenda agenda;





	public Intervalo(LocalDateTime fechayHora, Agenda agenda) {
		this.fechayHora = fechayHora;
		this.agenda = agenda;
	}
	public Intervalo() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getFechayHora() {
		return fechayHora;
	}

	public void setFechayHora(LocalDateTime fechayHora) {
		this.fechayHora = fechayHora;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	
}