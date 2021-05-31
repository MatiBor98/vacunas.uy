package datos.dtos;

import datos.entidades.Departamento;

import java.util.Objects;

public class VacunatorioDTO {

	private final String nombre;
	private final String ciudad;
	private final String direccion;
	private final Departamento departamento;


	public VacunatorioDTO(String nombre, String ciudad, String direccion, Departamento departamento) {
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.departamento = departamento;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VacunatorioDTO that = (VacunatorioDTO) o;
		return getNombre().equals(that.getNombre()) && getCiudad().equals(that.getCiudad()) &&
				getDireccion().equals(that.getDireccion()) && getDepartamento() == that.getDepartamento();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getNombre(), getCiudad(), getDireccion(), getDepartamento());
	}

	public String getNombre() {
		return nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public Departamento getDepartamento() {
		return departamento;
	}
}
