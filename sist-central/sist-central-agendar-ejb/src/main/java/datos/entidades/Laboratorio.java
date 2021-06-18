package datos.entidades;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class Laboratorio implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String nombre;
	@ManyToMany(mappedBy = "laboratorios")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Vacuna> vacunas;
	
	public Laboratorio() {
		super();
	}
	
	
	public Laboratorio(String nombre, List<Vacuna> vacs) {
		super();
		this.nombre = nombre;
		this.vacunas = vacs;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Vacuna> getVacunas() {
		return this.vacunas;
	}
	
	public void setVacunas(List<Vacuna> vacs) {
		this.vacunas = vacs;
	}
}
