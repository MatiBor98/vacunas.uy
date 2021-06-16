package datos.entidades;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Vacuna implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String nombre;
	@ManyToMany
	@JoinColumn(name="laboratorioNombre", nullable=false)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Laboratorio> laboratorios;
	@ManyToMany
	@JoinColumn(name="enfermedadNombre", nullable=false)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Enfermedad> enfermedades;
	@OneToMany(mappedBy = "vacuna")
	private List<Etapa> etapas;

	private int cantDosis;
	private int inmunidadMeses;
	private int dosisSeparacionDias;

	
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
	
	public List<Enfermedad> getEnfermedades() {
		return this.enfermedades;
	}

	public void setEnfermedades(List<Enfermedad> enfermedades) {
		this.enfermedades = enfermedades;
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
	
	public int getDosisSeparacionDias() {
		return this.dosisSeparacionDias;
	}

	public int getDosisSeparacionDiasMultiploSemana() {
		return (int) (7 * Math.ceil(this.dosisSeparacionDias/7d));
	}

	public void setDosisSeparacionDias(int cantDias) {
		this.dosisSeparacionDias = cantDias;
	}

	public Vacuna(List<Laboratorio> laboratorios, List<Enfermedad> enfermedades, String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacionDias) {
		super();
		this.nombre = nombre;
		this.laboratorios = laboratorios;
		this.enfermedades = enfermedades;
		this.cantDosis = cantDosis;
		this.inmunidadMeses = inmunidadMeses;
		this.dosisSeparacionDias = dosisSeparacionDias;
		this.etapas = new ArrayList<>();
	}

	public Vacuna() {
		super();
		this.etapas = new ArrayList<>();
	}

	public List<Etapa> getEtapas() {
		return etapas;
	}

	public void setEtapas(List<Etapa> etapas) {
		this.etapas = etapas;
	}
}
