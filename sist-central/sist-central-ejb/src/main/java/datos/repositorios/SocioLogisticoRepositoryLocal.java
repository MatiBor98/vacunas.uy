package datos.repositorios;

import datos.entidades.Lote;
import datos.entidades.SocioLogistico;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface SocioLogisticoRepositoryLocal {
	 List<SocioLogistico> find();
	 List<SocioLogistico> findHabilitados();
	 Optional<SocioLogistico> find(String nombre);
	 void save(SocioLogistico soc);
	 void habilitar(String nombre);
	List<Lote> getLotes(String nombreSocioLogistico);
	}
