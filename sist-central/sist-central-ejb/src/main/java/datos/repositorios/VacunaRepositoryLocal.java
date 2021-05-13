package datos.repositorios;

import java.util.List;

import javax.ejb.Local;

import datos.entidades.Enfermedad;
import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;

@Local
public interface VacunaRepositoryLocal {
	 public List<Vacuna> find();
	 public List<Vacuna> findByNombreVacuna(String nombreVac);
	 public void save(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, List<Laboratorio>labs, List<Enfermedad>enfs);
	 public void eliminar(String nombre);
}
