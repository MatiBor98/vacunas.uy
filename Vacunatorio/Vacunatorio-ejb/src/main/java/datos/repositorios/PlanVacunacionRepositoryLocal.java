package datos.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;
import datos.entidades.PlanVacunacion;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Vacunatorio;

@Local
public interface PlanVacunacionRepositoryLocal {
    public List<PlanVacunacion> find();
    public void save(PlanVacunacion plan);
    public Optional<PlanVacunacion> find(String nombre);
    public PlanVacunacion findByNombre(String criterio);
	public void save(String nombre, LocalDate inicio, LocalDate fin, String enfermedad);
	void drop();
}
