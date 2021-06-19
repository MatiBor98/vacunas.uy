package logica.servicios.local;

import datos.dtos.ReservaDTO;
import datos.entidades.Vacunatorio;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;

@Local
public interface ReservaServiceLocal {
	List<ReservaDTO> getReservasDTO(Vacunatorio vac);
	void confirmarVacuna(int idReserva, String idLote);
	void cancelarVacuna(int idReserva);
	Map<String, Integer> getDosisPorDepartamentos( String enfermedad, String vacuna, int etapa);
	Integer findVacunadosHoy(String enfermedad, String vacuna, int etapa);
	Integer findAgendadosProximos(String enfermedad, String vacuna, int etapa);
}
