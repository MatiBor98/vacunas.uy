package logica.servicios.local;

import datos.dtos.AgendaDTO;
import datos.dtos.IntervaloDTO;
import datos.dtos.VacunatorioTieneAgendaDTO;
import datos.entidades.Departamento;
import datos.entidades.Intervalo;
import datos.entidades.Reserva;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Local
public interface AgendaServiceLocal {
    List<AgendaDTO> find();
    Optional<AgendaDTO> find(int id);
    List<AgendaDTO> findByNombrePlan(String criterio);
    AgendaDTO save(AgendaDTO agendaDTO);
    List<VacunatorioTieneAgendaDTO>findAgendasParaCiudadanoPorDepartamento(
            String nombreEnfermedad, int edadCiudadano, Trabajo trabajos, Departamento departamento);
    List<Intervalo> getIntervalos(int agendaId, LocalDate fechaInicio);
    List<Reserva> efectuarReserva(Intervalo intervalo, int ciudadano);
	IntervaloDTO getIntervaloDTO(Intervalo intervalo);

}
