package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.Enfermedad;
import datos.entidades.PlanVacunacion;
import datos.entidades.Vacuna;

@Local
public interface EnfermedadRepositoryLocal {
	 public List<Enfermedad> find();
	 public List<Enfermedad> find(int primerResultado, int limiteResultados);
	 public Optional<Enfermedad> find(String nombre);
	 public List<Enfermedad> findByNombreEnfermedad(String nombreEnf);
	 public void save(String nombre, String descripcion, List<Vacuna> vacunas, List<PlanVacunacion> planesVacunacion);
	 public void eliminar(String nombre);
}
