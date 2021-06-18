package logica.servicios.remoto;

import datos.dtos.AgendaDTO;
import datos.dtos.VacunatorioTieneAgendaDTO;
import datos.entidades.Departamento;
import datos.entidades.Intervalo;
import plataformainteroperabilidad.Trabajo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AgendaServiceRemote {
    List<AgendaDTO> find();

    Optional<AgendaDTO> find(int id);

    List<AgendaDTO> findByNombrePlan(String criterio);

    List<VacunatorioTieneAgendaDTO> findAgendasParaCiudadanoPorDepartamento(String nombreEnfermedad, int edadCiudadano,
                                                                            Trabajo trabajos, Departamento departamento);

    List<Intervalo> getIntervalos(int agendaId, LocalDate fechaInicioSemana);
}
