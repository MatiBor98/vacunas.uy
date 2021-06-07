package laboratorio.tse.negocios;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import laboratorio.tse.entidades.Lote;

@Local
public interface LoteServiceLocal {
	void addLote(int dosisDisponibles, int numeroLote,String nombreVacunatorio, String nomVac, LocalDate fechaVencimiento, String vacunaNombre);
    List<Lote> find();
	void despacharLote(int numero,String nombreSocLog, LocalDate fechaDespacho);
	void entregarLote(int numero, LocalDate fechaEntrega);
	void transportarLoteAsync(int numero,String nombreSocLog);
	Optional<Lote> find(int numeroLote);


}
