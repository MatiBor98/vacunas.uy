package datos.repositorios;

import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;
import plataformainteroperabilidad.Trabajo;
import datos.entidades.Vacuna;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Local
public interface EtapaRepositoryLocal {
    List<Etapa> find();

    List<Etapa> findVigentes();

    void save(Etapa etapa);
    Optional<Etapa> find(int id);
    List<Etapa> find(String nombreEnfermedad, int edadCiudadano, Trabajo trabajos);
	void save(Vacuna vac, LocalDate inicio, LocalDate fin, PlanVacunacion planVacunacion, String descripcion, List<Trabajo> trabajos, int edadMin, int edadMax);
    boolean existeEtapaParaCiudadano(String nombreEnfermedad, int edadCiudadano, Trabajo trabajos);
}
