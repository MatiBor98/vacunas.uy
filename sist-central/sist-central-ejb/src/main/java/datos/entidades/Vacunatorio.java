package datos.entidades;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.vividsolutions.jts.geom.Point;




@Entity
@XmlRootElement
public class Vacunatorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String nombre;
	private String ciudad;
	private String direccion;
	@Enumerated(EnumType.STRING)
	private Departamento departamento;
	@OneToMany(mappedBy = "vacunatorio")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<PuestoVacunacion> puestosVacunacion;


	@OneToMany(mappedBy = "vacunatorio")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Turno> turnos;

	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Lote> lotes;
	
	@OneToMany
	private List<DosisVacunatorio> dosisVacunatorios;

	/*@Column(columnDefinition = "geometry(Point,4326)")
	private Point ubicacion;*/
	
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

	public Set<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(Set<Lote> lotes) {
		this.lotes = lotes;
	}

	/*public Point getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Point ubicacion) {
		this.ubicacion = ubicacion;
	}*/

	public Vacunatorio(String nombre, String ciudad, String direccion, Departamento departamento) {
		super();
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.departamento = departamento;
		this.puestosVacunacion = Collections.emptyList();
		this.dosisVacunatorios = Collections.emptyList();
		this.lotes = Collections.emptySet();
		this.turnos = Collections.emptyList();
		//this.ubicacion = null;
	}

	public Vacunatorio() {
		super();
	}
	
}
