package negocios;

import java.time.LocalDate;
import java.util.List;

import laboratorio.tse.entidades.Lote;

public interface LoteServiceLocal {
	void addLote(int dosisDisponibles, int numeroLote, String nomVac, LocalDate fechaVencimiento, String vacunaNombre);
    List<Lote> find();

}
