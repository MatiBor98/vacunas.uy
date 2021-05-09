package logica.servicios.local;

import datos.dtos.AgendaDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AgendaServiceLocal {
    List<AgendaDTO> find();
    List<AgendaDTO> findByNombrePlan(String criterio);
    void save(AgendaDTO agendaDTO);
}
