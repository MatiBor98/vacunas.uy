package datos.dtos;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.util.List;

public class VacunaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String nombre;
	private List<LaboratorioDTO> laboratorios;
	private List<EnfermedadDTO> enfermedades;
	private int cantDosis;
	private int inmunidadMeses;
	private int dosisSeparacionDias;

	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<LaboratorioDTO> getLaboratorios() {
		return laboratorios;
	}

	public void setLaboratorios(List<LaboratorioDTO> laboratorios) {
		this.laboratorios = laboratorios;
	}
	
	public List<EnfermedadDTO> getEnfermedades() {
		return this.enfermedades;
	}

	public void setEnfermedades(List<EnfermedadDTO> enfermedades) {
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
	public void setDosisSeparacionDias(int cantDias) {
		this.dosisSeparacionDias = cantDias;
	}

	public VacunaDTO(List<LaboratorioDTO> laboratorios, List<EnfermedadDTO> enfermedades, String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacionDias) {
		super();
		this.nombre = nombre;
		this.laboratorios = laboratorios;
		this.enfermedades = enfermedades;
		this.cantDosis = cantDosis;
		this.inmunidadMeses = inmunidadMeses;
		this.dosisSeparacionDias = dosisSeparacionDias;
	}

	public VacunaDTO() {
		super();
	}
}
