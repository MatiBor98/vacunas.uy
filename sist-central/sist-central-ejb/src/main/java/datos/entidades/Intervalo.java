package datos.entidades;

import java.io.Serializable;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Entity
public class Intervalo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="intervaloId")
    @SequenceGenerator(name="intervaloId",sequenceName="intervaloId", allocationSize=1)
	private Integer id;
	private LocalDateTime fechayHora;

	@ManyToOne
	@JoinColumn(name="agendaId", nullable=false)
	private Agenda agenda;

	@OneToMany(mappedBy = "intervalo")
	private List<Reserva> reservas;

	public Intervalo(LocalDateTime fechayHora, Agenda agenda) {
		this.fechayHora = fechayHora;
		this.agenda = agenda;
		this.reservas = Collections.emptyList();
	}
	public Intervalo() {}

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

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
}