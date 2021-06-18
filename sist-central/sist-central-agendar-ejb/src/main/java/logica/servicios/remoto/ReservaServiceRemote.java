package logica.servicios.remoto;

import datos.entidades.Intervalo;
import datos.entidades.Reserva;

import java.util.List;

public interface ReservaServiceRemote {
    List<Reserva> listar(int offset, int limit, int ci);

    Long listarCount(int ci);

    void cancelar(int ci, int codigo);

    List<Reserva> efectuarReserva(Intervalo intervalo, int ciudadanoCi);
}
