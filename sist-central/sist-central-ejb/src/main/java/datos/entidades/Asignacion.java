package datos.entidades;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Asignacion {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaAgendaId")
	@SequenceGenerator(name="asigId",sequenceName="asigId", allocationSize=1)
	private long id;
	
	private LocalDate fechaInicio, fechaFin;
	private PuestoVacunacion puestoVacunacion;
	private Turno turno;
	private Vacunador vacunador;
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public Asignacion(LocalDate fechaInicio, LocalDate fechaFin, PuestoVacunacion puestoVacunacion, Turno turno,
			Vacunador vacunador) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.puestoVacunacion = puestoVacunacion;
		this.turno = turno;
		this.vacunador = vacunador;
	}
	public Asignacion() {
		super();
	}
	
	
}
