package logica.servicios.local;

import datos.entidades.Departamento;
import datos.entidades.ReservaDomicilio;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;


@Local
public interface ReservaDomicilioBeanServiceLocal {
    List<ReservaDomicilio> findReservasDomicilioCiudadano(int offset, int limit, int ci);

    List<ReservaDomicilio> findReservasDomicilio(int offset, int limit);

    void efectuarReservaDomicilio(int ci, Departamento dep, String direccion, String localidad, String enfermedad, LocalDate fecha);

    void aceptarReservaDomicilio(int codigo);

    void rechazarReservaDomicilio(int codigo);

    void aceptarReservaDomicilioVacunacion(int codigo);

    void rechazarReservaDomicilioVacunacion(int codigo);
}
