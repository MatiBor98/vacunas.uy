package datos.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Turno implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaTurnoId")
	@SequenceGenerator(name="sequenciaTurnoId",sequenceName="sequenciaTurnoId", allocationSize=1)
	private int id;
	private String nombre;
	private LocalTime inicio;
	private LocalTime fin;

	@ManyToOne
	private Vacunatorio vacunatorio;

	@OneToMany(mappedBy="turno")
	private List<Asignacion> asignaciones;
	
	public Turno() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public List<Asignacion> getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(List<Asignacion> asignaciones) {
		this.asignaciones = asignaciones;
	}

	public Turno(String nombre, LocalTime inicio, LocalTime fin, Vacunatorio vacunatorio) {
		super();
		this.nombre = nombre;
		this.inicio = inicio;
		this.fin = fin;
		this.vacunatorio = vacunatorio;
		this.asignaciones = new ArrayList<Asignacion>();
	}
	
	
	
}
