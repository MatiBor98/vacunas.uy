package datos.entidades;

import datos.exceptions.IntervaloNoDisponibleException;

import java.io.Serializable;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

	@OneToMany(mappedBy = "intervalo")
	private List<Reserva> reservas;

	@Transient
	private final AtomicInteger cantidadReservasPendientes = new AtomicInteger(0);

	@PostLoad
	private void postLoad(){
		cantidadReservasPendientes.compareAndSet(0,
				(int) reservas.stream().map(Reserva::getEstado).filter(Estado.PENDIENTE::equals).count());
	}

	public Intervalo(LocalDateTime fechayHora, Agenda agenda) {
		this.fechayHora = fechayHora;
		this.agenda = agenda;
		this.reservas = new LinkedList<>();
	}

	public Intervalo() {
		this.reservas = new LinkedList<>();
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

	public List<Reserva> getReservas() {
		return new LinkedList<>(reservas);
	}

	/**
	 * Esto lo que busca es retornar una representacion del intervalo para usar como lock id
	 * en este caso no es unica ya que podría darse que la suma de la agendaId y el epoch coincidan
	 * con otra agenda e intervalo. Pero es muy pero muy poco probable y en el caso de que pasara
	 * el único efecto adverso sería esperar innecesariamente
	 * @return El identificador casi unico del intervalo.
	 */
	public long getLockId() {
		return this.getFechayHora().toEpochSecond(ZoneOffset.UTC) + this.agenda.getId();
	}

	public void addReserva(Reserva reserva) {
		int capacidadPorIntervalo = agenda.getHorarioPorDia().get(fechayHora.getDayOfWeek()).getCapacidadPorTurno();
		int cant;
		do {
			cant = cantidadReservasPendientes.get();
			if (cant >= capacidadPorIntervalo) {
				throw new IntervaloNoDisponibleException();
			}
		} while (!cantidadReservasPendientes.compareAndSet(cant, cant + 1));
		reservas.add(reserva);
	}

	public int getCantidadReservasPendientes() {
		return cantidadReservasPendientes.get();
	}
}