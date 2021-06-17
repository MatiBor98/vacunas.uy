package logica.servicios.remoto;

import datos.dtos.VacunatorioTieneAgendaDTO;
import datos.entidades.Departamento;
import datos.entidades.Intervalo;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.Remote;
import java.time.LocalDate;
import java.util.List;


@Remote
public interface AgendaServiceRemote {

    List<VacunatorioTieneAgendaDTO> findAgendasParaCiudadanoPorDepartamento(
            String nombreEnfermedad, int edadCiudadano, Trabajo trabajos, Departamento departamento);

    List<Intervalo> getIntervalos(int agendaId, LocalDate fechaInicio);

}
