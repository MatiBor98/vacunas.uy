package laboratorio.tse.negocios;

import java.time.LocalDate;

import java.util.List;

import javax.ejb.Local;

import laboratorio.tse.entidades.Lote;

@Local
public interface LoteServiceLocal {
	void addLote(int dosisDisponibles, int numeroLote,String nombreVacunatorio, String nomVac, LocalDate fechaVencimiento, String vacunaNombre);
    List<Lote> find();
	void despacharLote(int numero, LocalDate fechaDespacho);
	void entregarLote(int numero, LocalDate fechaEntrega);
	void transportarLoteAsync(int numero,String nombreSocLog);

}
