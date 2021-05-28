package datos.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;
import javax.persistence.TypedQuery;

import datos.entidades.Enfermedad;
import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;
import datos.entidades.RestriccionEtapa;
import datos.entidades.Trabajos;
import datos.entidades.Vacuna;

@Local
public interface EtapaRepositoryLocal {

    public List<Etapa> find();
    public void save(Etapa etapa);

    public Optional<Etapa> find(int id);

    public List<Etapa> find(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos);

	public void save(Vacuna vac, LocalDate inicio, LocalDate fin, PlanVacunacion planVacunacion, String descripcion, List<Trabajos> trabajos, int edadMin, int edadMax);
}
