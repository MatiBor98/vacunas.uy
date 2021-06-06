package datos.repositorios;

import java.util.List;

import javax.ejb.Local;

import datos.entidades.Enfermedad;
import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;

@Local
public interface LaboratorioRepositoryLocal {
	 public List<Laboratorio> find();
	 public List<Laboratorio> findByNombreLaboratorio(String nombrelab);
	 public void save(String nombre, List<Vacuna> vacs);
	 public void eliminar(String nombre);
}
