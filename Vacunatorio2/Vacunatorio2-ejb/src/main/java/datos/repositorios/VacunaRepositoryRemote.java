package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Remote;

import org.hibernate.mapping.Collection;

import datos.entidades.Vacuna;

@Remote
public interface VacunaRepositoryRemote {
	 List<Vacuna> find();
	 public Optional<Vacuna> find(String nombre);
	 List<Vacuna> findByNombreVacuna(String nombreVac);
	 void save(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, Collection labs, Collection enfs);
	 void eliminar(String nombre);
	 void modificarVacuna(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, Collection labs, Collection enfs);
	 void drop();
}
