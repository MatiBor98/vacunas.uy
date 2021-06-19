package datos.dtos;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;
import java.util.Map;

public class LaboratorioDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private List<VacunaDTO> vacunas;
	
	public LaboratorioDTO() {
		super();
	}
	
	
	public LaboratorioDTO(String nombre, List<VacunaDTO> vacs) {
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
	
	public List<VacunaDTO> getVacunas() {
		return this.vacunas;
	}
	
	public void setVacunas(List<VacunaDTO> vacs) {
		this.vacunas = vacs;
	}
}
