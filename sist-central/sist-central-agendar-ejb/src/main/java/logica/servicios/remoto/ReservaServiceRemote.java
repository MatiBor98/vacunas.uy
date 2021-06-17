package logica.servicios.remoto;

import datos.entidades.Intervalo;
import datos.entidades.Reserva;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ReservaServiceRemote {
    List<Reserva> listar(int offset, int limit, int ci);

    Long listarCount(int ci);

    List<Reserva> efectuarReserva(Intervalo intervalo, int ciudadanoCi);

    void cancelar(int ci, int codigo);

    boolean existeReservaPendienteByCiudadanoEnfermedad(int ci, String enfermedad);

}
