package laboratorio.tse.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import laboratorio.tse.entidades.Lote;

public interface LoteRepositoryLocal {

	List<Lote> find();
	
	Optional<Lote> find(int numeroLote);

	void save(Lote lote);

	void despacharLote(int numero, LocalDate fechaDespacho);
	void entregarLote(int numero, LocalDate fechaEntrega);
    public void transportarLoteAsync(int numero);


}
