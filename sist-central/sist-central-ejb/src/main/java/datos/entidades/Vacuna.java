package datos.entidades;

import java.io.Serializable;
import datos.entidades.Laboratorio;
@Entity
public class Vacuna implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String nombre;
	private Map<String, Laboratorio> laboratorios;
	private int cantDosis;
	private int inmunidadMeses;
	
	public Vacuna() {
		
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
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
}
