package datos.entidades;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Asignacion {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="asigId")
	@SequenceGenerator(name="asigId",sequenceName="asigId", allocationSize=1)
	private int id;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;

	@ManyToOne
	@JoinColumn(name="puestoVacunacion", nullable=false)
	private PuestoVacunacion puestoVacunacion;

	@ManyToOne
	@JoinColumn(name="ciVacunador", nullable=false)
	private Vacunador vacunador;

	@ManyToOne
	@JoinColumn(name="turnoId", nullable=false)
	private Turno turno;

	public Asignacion() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public PuestoVacunacion getPuestoVacunacion() {
		return puestoVacunacion;
	}

	public void setPuestoVacunacion(PuestoVacunacion puestoVacunacion) {
		this.puestoVacunacion = puestoVacunacion;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Vacunador getVacunador() {
		return vacunador;
	}

	public void setVacunador(Vacunador vacunador) {
		this.vacunador = vacunador;
	}
}
