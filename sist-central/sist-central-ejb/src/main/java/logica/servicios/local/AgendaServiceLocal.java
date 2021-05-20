package logica.servicios.local;

import datos.dtos.AgendaDTO;
import datos.entidades.*;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Local
public interface AgendaServiceLocal {
    List<AgendaDTO> find();
    Optional<AgendaDTO> find(int id);
    List<AgendaDTO> findByNombrePlan(String criterio);
    AgendaDTO save(AgendaDTO agendaDTO);
    Map<Vacunatorio, List<AgendaDTO>> findAgendasParaCiudadanoPorDepartamento(
            String nombreEnfermedad, int edadCiudadano, Trabajos trabajos, Departamento departamento);
    List<Intervalo> getIntervalos(int agendaId, LocalDate fechaInicio);
}
