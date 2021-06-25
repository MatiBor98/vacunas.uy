package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.Enfermedad;
import datos.entidades.Etapa;
import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;

@Local
public interface VacunaRepositoryLocal {
	 List<Vacuna> find();
	 public Optional<Vacuna> find(String nombre);
	 List<Vacuna> findByNombreVacuna(String nombreVac);
	 void save(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, List<Laboratorio>labs, List<Enfermedad>enfs);
	 void eliminar(String nombre);
	 void modificarVacuna(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, List<Laboratorio>labs, List<Enfermedad>enfs);
	 List<Vacuna> findByEnfermedad(String enfermedad);

	List<Etapa> getEtapas(String nombreVacuna);
}
