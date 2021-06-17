package datos.repositorios;

import datos.entidades.Agenda;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface AgendaRepositoryLocal {
    List<Agenda> find();

    Optional<Agenda> find(int id);

    void save(Agenda agenda);

    List<Agenda> findByNombrePlan(String criterio);
}
