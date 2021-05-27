package datos.repositorios;

import datos.entidades.SocioLogistico;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface SocioLogisticoRepositoryLocal {
	 List<SocioLogistico> find();
     Optional<SocioLogistico> find(String nombre);
	 void save(SocioLogistico soc);
	 void habilitar(String nombre);
	}
