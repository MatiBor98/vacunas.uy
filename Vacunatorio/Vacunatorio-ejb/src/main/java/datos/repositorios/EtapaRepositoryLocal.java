package datos.repositorios;

import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;
import datos.entidades.Trabajos;
import datos.entidades.Vacuna;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Local
public interface EtapaRepositoryLocal {
    List<Etapa> find();
    void save(Etapa etapa);
    Optional<Etapa> find(int id);
    List<Etapa> find(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos);
	void save(Vacuna vac, LocalDate inicio, LocalDate fin, PlanVacunacion planVacunacion, String descripcion, List<Trabajos> trabajos, int edadMin, int edadMax);
    boolean existeEtapaParaCiudadano(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos);
}
