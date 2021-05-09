package datos.entidades;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Turno {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaTurnoId")
	@SequenceGenerator(name="sequenciaTurnoId",sequenceName="sequenciaTurnoId", allocationSize=1)
	private long id;
	private String nombre;
	private LocalTime inicio;
	private LocalTime fin;

	@ManyToOne
	private Vacunatorio vacunatorio;

	public Turno() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalTime inicio) {
		this.inicio = inicio;
	}

	public LocalTime getFin() {
		return fin;
	}

	public void setFin(LocalTime fin) {
		this.fin = fin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Vacunatorio getVacunatorio() {
		return vacunatorio;
	}

	public void setVacunatorio(Vacunatorio vacunatorio) {
		this.vacunatorio = vacunatorio;
	}
}
