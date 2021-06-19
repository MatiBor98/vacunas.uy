package datos.repositorios;

import datos.entidades.Lote;
import datos.entidades.Turno;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.ejb.Remote;

@Remote
public interface LoteRepositoryRemote {
	Optional<Lote> findByNumLote(int numeroLote);
    List<Lote> find();
    //public List<Lote> find(String vac, String nombreLote);
    void save(Lote lote);
	void drop();

    }