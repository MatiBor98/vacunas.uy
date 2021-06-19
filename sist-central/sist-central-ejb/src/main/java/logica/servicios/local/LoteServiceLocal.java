package logica.servicios.local;

import datos.dtos.LoteDTO;
import datos.entidades.Lote;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Local
public interface LoteServiceLocal {
    void addLote(int dosisDisponibles, int numeroLote, String nomVac, LocalDate fechaVencimiento, String vacunaNombre, String socioLogisticoNombre);
    Optional<Lote> findById(int numLote);
    List<Lote> find();
    void despacharLote(int numeroLote, String socioLogistico, LocalDate fechaDespacho);
    void entregarLote(int numeroLote, String socioLogistico, LocalDate fechaEntrega);
    //List<Lote> find(String vac, String nombreLote);
	LoteDTO getLoteDTO(Lote lote);
	void decrementar(int valueOf);
}
