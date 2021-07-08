package logica.servicios.local;

import java.util.List;

import javax.ejb.Local;

import datos.dtos.ReservaDTO;
import datos.entidades.Departamento;
import datos.entidades.Intervalo;
import datos.entidades.Reserva;
import datos.entidades.ReservaDomicilio;
import datos.entidades.Vacunatorio;
@Local
public interface ReservaServiceLocal {

	List<Reserva> efectuarReserva(Intervalo intervalo, int ciudadanoCi);

	List<ReservaDTO> getReservasDTO(Vacunatorio vac);
	void confirmarVacuna(int idReserva, String idLote);
	void cancelarVacuna(int idReserva);
	
    List<ReservaDomicilio> findReservasDomicilioCiudadano(int offset, int limit, int ci);
    List<ReservaDomicilio> findReservasDomicilio(int offset, int limit);
    void saveReservaDomicilio(int ci, Departamento dep, String direccion, String localidad, int paraDosis, int etapaId);

}
