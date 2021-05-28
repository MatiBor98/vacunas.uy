package logica.servicios.local;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.Departamento;
import datos.entidades.Lote;
import datos.entidades.Vacunatorio;

@Local
public interface VacunatorioControllerLocal {
	void addVacunatorio(String nombre, String ciudad, String direccion, Departamento departamento);
	Optional<Vacunatorio> find(String nombre);
	Optional<Vacunatorio> findWithEverything(String nombre);
	List<Vacunatorio> find();
	void addPuestoAlVacunatorio(String nombreVacunaotorio, int idPuesto);
	int addTurno(String nombreTurno, LocalTime inicio, LocalTime fin, String nombreVacunatorio);
	List<String> getNombresDepartamentos();
	List<Vacunatorio> findByDepartamento(Departamento dep, int primerResultado, int maximosResultados); 
	List<Vacunatorio> findByDepartamento(Departamento dep);
}
