package datos.repositorios;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.dtos.VacunatorioDTO;
import datos.entidades.Departamento;
import datos.entidades.Lote;
import datos.entidades.Vacunatorio;

@Local
public interface VacunatorioRepositoryLocal {
	 List<Vacunatorio> find();
	 List<Vacunatorio> find(int primerResultado, int maximosResultados);
	 List<Vacunatorio> findByDepartamento(Departamento dep, int primerResultado, int maximosResultados);
	 List<Vacunatorio> findByDepartamento(Departamento dep);
	 void save(Vacunatorio vac);
	 void addLoteAVacuantorio(Lote lote, String nombreVacunatorio);
	 Optional<Vacunatorio> find(String nombre);
	 Optional<Vacunatorio> findWithEverything(String nombre);
	 void drop();
}
