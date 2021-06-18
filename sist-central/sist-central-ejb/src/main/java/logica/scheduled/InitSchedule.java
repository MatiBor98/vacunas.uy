package logica.scheduled;

import datos.entidades.*;
import datos.entidades.reporteStockDosis.DatosDosis;
import datos.repositorios.DosisReporteReporteBean;
import datos.repositorios.ReservaRepository;
import datos.repositorios.VacunatorioRepositoryLocal;
import logica.servicios.local.CiudadanoServiceLocal;
import logica.servicios.local.LoteServiceLocal;

import javax.ejb.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

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
                    String.format("Se recuerda que %s (%s), tiene una reserva para: %s.",
                            reserva.getCiudadano().getNombre(),
                            String.valueOf(reserva.getCiudadano().getCi()),
                            reserva.getIntervalo().getFechayHora().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)))
            );
        }
    }

    @EJB
    VacunatorioRepositoryLocal vacunatorioRepositoryLocal;
    @EJB
    DosisReporteReporteBean dosisReporteReporteBean;

    // 1 am, para el dia anterior
    @Schedule(hour = "1", minute = "0", second = "0")
    public void guardarDatosStockDosis(){
        LocalDate hoy = LocalDate.now();
        List<Vacunatorio> vacunatorios = vacunatorioRepositoryLocal.find();

        List<DatosDosis> datosDosisList = new ArrayList<>();
        //Calcular
        for (Vacunatorio vac: vacunatorios){
            Set<Lote> lotes = vac.getLotes();
            Map<String, DatosDosis> datosVacunatorio = new HashMap<>();
            for (Lote lot : lotes){
                Vacuna vacunaActual = lot.getVacuna();
                String nomVacuna = vacunaActual.getNombre();
                //Primer vez de esta vacuna
                if (datosVacunatorio.get(nomVacuna) == null){
                    datosVacunatorio.put( nomVacuna , new DatosDosis(hoy, vac.getNombre(),nomVacuna, lot.getDosisDisponibles()));
                }
                // ya existe en map
                else {
                    Integer cantidadAntes = datosVacunatorio.get(nomVacuna).getCantidad();
                    datosVacunatorio.get(nomVacuna).setCantidad(cantidadAntes + lot.getDosisDisponibles());
                }
            }
            datosDosisList.addAll(datosVacunatorio.values());
        }
        // Guardar
        dosisReporteReporteBean.saveAll(datosDosisList);
    }
}
