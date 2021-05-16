package datos.entidades;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;
import java.util.Map;

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
