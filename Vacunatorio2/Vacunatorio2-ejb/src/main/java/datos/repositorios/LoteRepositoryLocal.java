package datos.repositorios;

import datos.entidades.Lote;
import datos.entidades.Turno;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

@Local
public interface LoteRepositoryLocal {
    Optional<Lote> findByNumLote(int numeroLote);
    List<Lote> find();
    //public List<Lote> find(String vac, String nombreLote);
    void save(Lote lote);
    void drop();
}