package logica.servicios.local;

import datos.dtos.ReservaDTO;
import datos.entidades.Vacunatorio;

import javax.ejb.Local;
import java.util.List;
@Local
public interface ReservaServiceLocal {

	List<ReservaDTO> getReservasDTO(Vacunatorio vac);
	void confirmarVacuna(int idReserva, String idLote);
	void cancelarVacuna(int idReserva);

}
