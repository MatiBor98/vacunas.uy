package logica.servicios.local;

import java.util.List;

import javax.ejb.Local;

import datos.dtos.ReservaDTO;
import datos.entidades.Intervalo;
import datos.entidades.Reserva;
import datos.entidades.Vacunatorio;
@Local
public interface ReservaServiceLocal {

	List<Reserva> efectuarReserva(Intervalo intervalo, int ciudadanoCi);

	List<ReservaDTO> getReservasDTO(Vacunatorio vac);
	void confirmarVacuna(int idReserva, String idLote);
	void cancelarVacuna(int idReserva);

}
