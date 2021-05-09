package datos.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;
@Entity
public class Vacuna implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String nombre;
	@ManyToMany
	@JoinColumn(name="vacunatorio_id", nullable=false)
	private List<Laboratorio> laboratorios;
	private int cantDosis;
	private int inmunidadMeses;

	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Laboratorio> getLaboratorios() {
		return laboratorios;
	}

	public void setLaboratorios(List<Laboratorio> laboratorios) {
		this.laboratorios = laboratorios;
	}

	public int getCantDosis() {
		return cantDosis;
	}
	public void setCantDosis(int cantDosis) {
		this.cantDosis = cantDosis;
	}
	public int getInmunidadMeses() {
		return this.inmunidadMeses;
	}
	public void setInmunidadMeses(int inmMeses) {
		this.inmunidadMeses = inmMeses;
	}

	public Vacuna(List<Laboratorio> laboratorios, int cantDosis, int inmunidadMeses) {
		super();
		this.laboratorios = laboratorios;
		this.cantDosis = cantDosis;
		this.inmunidadMeses = inmunidadMeses;
	}

	public Vacuna() {
		super();
	}
}
