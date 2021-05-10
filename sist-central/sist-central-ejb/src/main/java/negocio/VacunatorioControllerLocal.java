package negocio;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.Departamento;
import datos.entidades.Vacunatorio;

@Local
public interface VacunatorioControllerLocal {
	public void addVacunatorio(String nombre, String ciudad, String direccion, Departamento departamento);
	public Optional<Vacunatorio> getVacunatorio(String nombre);
	public List<Vacunatorio> getAllVacunatorio();
}
