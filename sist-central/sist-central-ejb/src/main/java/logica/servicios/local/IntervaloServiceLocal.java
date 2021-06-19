package logica.servicios.local;

import datos.entidades.Intervalo;

import java.time.LocalDate;
import java.util.List;

public interface IntervaloServiceLocal {
    List<Intervalo> getIntervalosByAgendaAndSemana(int agendaId, LocalDate fechaInicioSemana);
}
