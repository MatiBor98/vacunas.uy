package datos.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
public class Vacunatorio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String nombre;
	private String ciudad, direccion;
	private Departamento departamento;
	@OneToMany
	private List<PuestoVacunacion> puestosVacunacion;
	@ManyToMany
	private List<Vacuna> vacunasDisponibles;
	private List<Turno> turnos;
	private List<Lote> lotes;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public List<Vacuna> getVacunasDisponibles() {
		return vacunasDisponibles;
	}
	public void setVacunasDisponibles(ArrayList<Vacuna> vacunasDisponibles) {
		this.vacunasDisponibles = vacunasDisponibles;
	}

	public Vacunatorio(String nombre, String ciudad, String direccion, Departamento departamento) {
		super();
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.departamento = departamento;
		this.vacunasDisponibles = new ArrayList<Vacuna>();
		this.turnos = new ArrayList<Turno>();
		this.lotes = new ArrayList<Lote>();

	}
	public Vacunatorio() {
		super();
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public List<PuestoVacunacion> getPuestosVacunacion() {
		return puestosVacunacion;
	}
	public void setPuestosVacunacion(List<PuestoVacunacion> puestosVacunacion) {
		this.puestosVacunacion = puestosVacunacion;
	}
	public List<Turno> getTurnos() {
		return turnos;
	}
	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}
	public List<Lote> getLotes() {
		return lotes;
	}
	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}
	public void setVacunasDisponibles(List<Vacuna> vacunasDisponibles) {
		this.vacunasDisponibles = vacunasDisponibles;
	}

	
	
	

	
}
