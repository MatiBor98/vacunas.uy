package datos.repositorios;

import datos.entidades.Agenda;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AgendaRepositoryLocal {
    List<Agenda> find();
    void save(Agenda agenda);
    List<Agenda> findByNombrePlan(String criterio);
}
