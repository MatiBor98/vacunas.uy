package logica.servicios.local;

import datos.entidades.Departamento;
import datos.entidades.Vacunatorio;
import logica.schedule.DatosVacunatorio;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface VacunatorioControllerLocal {
	void addVacunatorio(String nombre, String ciudad, String direccion, Departamento departamento);
	Optional<Vacunatorio> find(String nombre);
	Optional<Vacunatorio> findWithEverything(String nombre);
	List<Vacunatorio> find();
	//int addTurno(String nombreTurno, LocalTime inicio, LocalTime fin, String nombreVacunatorio);
	List<String> getNombresDepartamentos();
	List<Vacunatorio> findByDepartamento(Departamento dep, int primerResultado, int maximosResultados); 
	List<Vacunatorio> findByDepartamento(Departamento dep);
	DatosVacunatorio getDatosVacunatorio(String nombrevac);
}
