package datos.repositorios;

import datos.dtos.EtapaDTO2;
import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;
import datos.entidades.Trabajos;
import datos.entidades.Vacuna;

import javax.ejb.Remote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Remote
public interface EtapaRepositoryRemote {
    List<Etapa> find();
    void save(Etapa etapa);
    Optional<Etapa> find(int id);
    List<Etapa> find(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos);
	void save(String vac, LocalDate inicio, LocalDate fin, PlanVacunacion planVacunacion, String descripcion, List<Trabajos> trabajos, int edadMin, int edadMax);
    boolean existeEtapaParaCiudadano(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos);
	Etapa find(EtapaDTO2 etapaDTO);
	void drop();
}
