package logica.scheduled;

import datos.entidades.Reserva;
import datos.repositorios.ReservaRepository;
import logica.servicios.local.CiudadanoServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Singleton
@Startup
public class InitSchedule {

    @EJB
    ReservaRepository reservaRepository;

    @EJB
    CiudadanoServiceLocal ciudadanoServiceLocal;


    // Todos los dias a las 6 de la mañana
    @Schedule(hour = "6", minute = "0", second = "0")
    public void notificarReservasMovil(){
        List<Reserva> reservas = reservaRepository.findReservasTomorrow();
        for (Reserva reserva : reservas){
            ciudadanoServiceLocal.notificar(reserva.getCiudadano().getFirebaseTokenMovil(),
                    "Recordatorio de vacunación",
                    String.format("Se recuerda que %s (%s), tiene una reserva para %s.",
                            reserva.getCiudadano().getNombre(),
                            String.valueOf(reserva.getCiudadano().getCi()),
                            reserva.getIntervalo().getFechayHora().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)))
            );
        }
    }

}
