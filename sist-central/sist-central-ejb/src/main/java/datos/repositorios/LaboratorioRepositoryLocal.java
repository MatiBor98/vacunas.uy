package datos.repositorios;

import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;

import javax.ejb.Local;
import java.util.List;

@Local
public interface LaboratorioRepositoryLocal {
	 public List<Laboratorio> find();
	 public List<Laboratorio> findByNombreLaboratorio(String nombrelab);
	 public void save(String nombre, List<Vacuna> vacs);
	 public void eliminar(String nombre);
}
