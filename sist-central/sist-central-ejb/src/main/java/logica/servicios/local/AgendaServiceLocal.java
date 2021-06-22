package logica.servicios.local;

import datos.dtos.*;
import datos.entidades.*;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Local
public interface AgendaServiceLocal {
    List<AgendaDTO> find();

    void eliminar(int agendaId);

    List<VacunatorioTieneAgendaParaEtapaDTO> find(int offSet, int size);

    long findCount(String criterio);

    List<VacunatorioTieneAgendaParaEtapaDTO> findByNombrePlan(int offSet, int size, String criterio);

    Optional<AgendaDTO> find(int id);
    List<AgendaDTO> findByNombrePlan(String criterio);
    AgendaDTO save(AgendaDTO agendaDTO);
    List<VacunatorioTieneAgendaDTO>findAgendasParaCiudadanoPorDepartamento(
            String nombreEnfermedad, int edadCiudadano, Trabajo trabajos, Departamento departamento);
	IntervaloDTO2 getIntervaloDTO(Intervalo intervalo);


}
