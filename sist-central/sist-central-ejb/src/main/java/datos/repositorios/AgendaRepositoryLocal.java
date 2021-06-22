package datos.repositorios;

import datos.entidades.Agenda;
import datos.entidades.Departamento;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface AgendaRepositoryLocal {
    List<Agenda> find(int offSet, int size);

    List<Agenda> findByNombrePlan(int offSet, int size, String criterio);

    List<Agenda> find();
    Optional<Agenda> find(int id);
    List<Agenda> findAgendasParaCiudadanoPorDepartamento(String nombreEnfermedad, int edadCiudadano, Trabajo trabajos,
                                                         Departamento departamento);
    void save(Agenda agenda);
    List<Agenda> findByNombrePlan(String criterio);

    long findCount(String criterio);
}
