package logica.servicios.local;

import datos.entidades.Lote;
import datos.entidades.Turno;

import javax.ejb.Local;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Local
public interface LoteServiceLocal {
    void addLote(int dosisDisponibles, int numeroLote, String nomVac, LocalDate fechaVencimiento, String vacunaNombre, String socioLogisticoNombre);
    Optional<Lote> findById(int numLote);
    List<Lote> find();
    //List<Lote> find(String vac, String nombreLote);
}
