package laboratorio.tse.repositorios;

import java.util.List;

import laboratorio.tse.entidades.Lote;

public interface LoteRepositoryLocal {

	List<Lote> find();

	void save(Lote lote);

}
