package datos.entidades;

import java.io.Serializable;

import javax.persistence.*;

import datos.entidades.Laboratorio;
import java.time.LocalDate;
import java.time.LocalTime;
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
	private Long id;
	private LocalDate fecha;
	private LocalTime horaInicio;
	@ManyToOne
	@JoinColumn(name="agendaId", nullable=false)
	private Agenda agenda;

	@OneToMany(mappedBy = "intervalo")
	private List<Reserva> reservas;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public LocalTime getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
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
	public Intervalo(LocalDate fecha, LocalTime horaInicio, Agenda agenda) {
		super();
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.agenda = agenda;
	}
	public Intervalo() {
		super();
	}
	
	
}