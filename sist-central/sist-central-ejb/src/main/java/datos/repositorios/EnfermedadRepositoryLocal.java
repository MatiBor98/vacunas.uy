package datos.repositorios;

import datos.entidades.Enfermedad;
import datos.entidades.PlanVacunacion;
import datos.entidades.Vacuna;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface EnfermedadRepositoryLocal {
	List<Enfermedad> find();
	List<Enfermedad> find(int primerResultado, int limiteResultados, String criterio);

	List<Enfermedad> find(int primerResultado, int limiteResultados);

	Optional<Enfermedad> find(String nombre);
	List<Enfermedad> findByNombreEnfermedad(String nombreEnf);
	void save(String nombre, String descripcion, List<Vacuna> vacunas, List<PlanVacunacion> planesVacunacion);
	void eliminar(String nombre);
}
