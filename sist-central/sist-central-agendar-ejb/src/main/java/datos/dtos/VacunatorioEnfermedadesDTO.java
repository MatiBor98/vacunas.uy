package datos.dtos;

import datos.entidades.Departamento;

import java.io.Serializable;
import java.util.List;

public class VacunatorioEnfermedadesDTO implements Serializable{

	private String nombre;
	private String ciudad;
	private String direccion;
	private Departamento departamento;
	private List<String> enfermedades;
	
	private static final long serialVersionUID = 1L;

	
	
	public VacunatorioEnfermedadesDTO() {
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



	public List<String> getEnfermedades() {
		return enfermedades;
	}



	public void setEnfermedades(List<String> enfermedades) {
		this.enfermedades = enfermedades;
	}



	public VacunatorioEnfermedadesDTO(String nombre, String ciudad, String direccion, Departamento departamento,
			List<String> enfermedades) {
		super();
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.departamento = departamento;
		this.enfermedades = enfermedades;
	}

	
	
}
