package datos.repositorios;

import datos.entidades.Enfermedad;
import datos.entidades.PlanVacunacion;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Local
public interface PlanVacunacionRepositoryLocal {
    public List<PlanVacunacion> find();
    public void save(PlanVacunacion plan);
    public Optional<PlanVacunacion> find(String nombre);
	public void save(String nombre, LocalDate inicio, LocalDate fin, Enfermedad enfermedad);
}
