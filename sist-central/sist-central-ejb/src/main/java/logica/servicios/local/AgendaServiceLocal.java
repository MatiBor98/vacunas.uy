package logica.servicios.local;

import datos.dtos.AgendaDTO;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface AgendaServiceLocal {
    List<AgendaDTO> find();
    Optional<AgendaDTO> find(int id);
    List<AgendaDTO> findByNombrePlan(String criterio);
    AgendaDTO save(AgendaDTO agendaDTO);
}
