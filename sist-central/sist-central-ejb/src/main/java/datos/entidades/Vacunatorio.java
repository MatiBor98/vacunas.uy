package datos.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
public class Vacunatorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaVacunatorioId")
	@SequenceGenerator(name="sequenciaVacunatorioId",sequenceName="sequenciaVacunatorioId", allocationSize=1)
	private long id;
	private String nombre;
	private String ciudad;
	private String direccion;
	private Departamento departamento;
	@OneToMany(mappedBy = "vacunatorio")
	private List<PuestoVacunacion> puestosVacunacion;


	@OneToMany(mappedBy = "vacunatorio")
	private List<Turno> turnos;

	@OneToMany
	private List<Lote> lotes;
	
	@OneToMany
	private List<DosisVacunatorio> dosisVacunatorios;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
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
	public Vacunatorio(String nombre, String ciudad, String direccion, Departamento departamento) {
		super();
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.departamento = departamento;
		this.puestosVacunacion = new ArrayList<PuestoVacunacion>();
		this.dosisVacunatorios = new ArrayList<DosisVacunatorio>();
		this.lotes = new ArrayList<Lote>();
		this.turnos = new ArrayList<Turno>();
				
	}

	public Vacunatorio() {
		super();
	}
	
}
