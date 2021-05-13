package logica.servicios.local;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.Departamento;
import datos.entidades.Vacunatorio;

@Local
public interface VacunatorioControllerLocal {
	public void addVacunatorio(String nombre, String ciudad, String direccion, Departamento departamento);
	public Optional<Vacunatorio> find(String nombre);
	public Optional<Vacunatorio> findWithEverything(String nombre);
	public List<Vacunatorio> find();
	public void addPuestoAlVacunatorio(String nombreVacunaotorio, long idPuesto);
	public long addTurno(String nombreTurno, LocalTime inicio, LocalTime fin, String nombreVacunatorio);
}
