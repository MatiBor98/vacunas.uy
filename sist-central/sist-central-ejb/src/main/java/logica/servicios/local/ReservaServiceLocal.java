package logica.servicios.local;

import java.time.LocalDate;
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
	
    /*List<ReservaDomicilio> findReservasDomicilioCiudadano(int offset, int limit, int ci);
    List<ReservaDomicilio> findReservasDomicilio(int offset, int limit);
    void efectuarReservaDomicilio(int ci, Departamento dep, String direccion, String localidad, int etapaId, String detalles);
    */
	List<ReservaDTO> findReservaDomicilio(int offset, int limit);
	void aceptarReservaDomicilio(int codigo);
	void rechazarReservaDomicilio(int codigo);

    List<Reserva> efectuarReservaDomicilio(Intervalo intervalo, int ciudadanoCi, String localidad, String direccion, String detalles);

}
