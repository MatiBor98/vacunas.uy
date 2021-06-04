package laboratorio.tse.negocios;

import java.time.LocalDate;
import java.util.List;

import laboratorio.tse.entidades.Lote;

public interface LoteServiceLocal {
	void addLote(int dosisDisponibles, int numeroLote,String nombreVacunatorio, String nomVac, LocalDate fechaVencimiento, String vacunaNombre);
    List<Lote> find();
	void despacharLote(int numero);

}
