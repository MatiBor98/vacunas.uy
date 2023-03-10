package datos.dtos;

import datos.entidades.Departamento;
import datos.entidades.DosisVacunatorio;
import datos.entidades.Lote;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Turno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class VacunatorioDTO implements Serializable{
	
	static final long serialVersionUID = 1L;
	
	private String nombre;
	private String ciudad;
	private String direccion;
	private Departamento departamento;
	private List<PuestoVacunacionDTO> puestosVacunacion;
	private List<TurnoDTO> turnos;
	private Set<LoteDTO> lotes;
	private List<DosisVacunatorioDTO> dosisVacunatorios;


	public VacunatorioDTO(String nombre, String ciudad, String direccion, Departamento departamento) {
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.departamento = departamento;
		this.puestosVacunacion = new ArrayList<>();
		this.turnos = new ArrayList<>();
		this.lotes = new HashSet<>();
		this.dosisVacunatorios = new ArrayList<>();
	}
	
	public VacunatorioDTO(String nombre, String ciudad, String direccion, Departamento departamento, List<PuestoVacunacionDTO> puestosVacunacion, List<TurnoDTO> turnos, Set<LoteDTO> lotes, List<DosisVacunatorioDTO> dosisVacunatorios) {
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.departamento = departamento;
	}
	
	public VacunatorioDTO() {}

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

	public List<PuestoVacunacionDTO> getPuestosVacunacion() {
		return puestosVacunacion;
	}

	public void setPuestosVacunacion(List<PuestoVacunacionDTO> puestosVacunacion) {
		this.puestosVacunacion = puestosVacunacion;
	}

	public List<TurnoDTO> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<TurnoDTO> turnos) {
		this.turnos = turnos;
	}

	public Set<LoteDTO> getLotes() {
		return lotes;
	}

	public void setLotes(Set<LoteDTO> lotes) {
		this.lotes = lotes;
	}

	public List<DosisVacunatorioDTO> getDosisVacunatorios() {
		return dosisVacunatorios;
	}

	public void setDosisVacunatorios(List<DosisVacunatorioDTO> dosisVacunatorios) {
		this.dosisVacunatorios = dosisVacunatorios;
	}
}
