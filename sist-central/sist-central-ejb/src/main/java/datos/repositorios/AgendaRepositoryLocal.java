package datos.repositorios;

import datos.entidades.Agenda;
import datos.entidades.Departamento;
import datos.entidades.Trabajos;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface AgendaRepositoryLocal {
    List<Agenda> find();
    Optional<Agenda> find(int id);
    List<Agenda> findAgendasParaCiudadanoPorDepartamento(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos,
                                                         Departamento departamento);
    void save(Agenda agenda);
    List<Agenda> findByNombrePlan(String criterio);
}
