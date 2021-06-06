package datos.repositorios;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Agenda;
import datos.entidades.Ciudadano;
import datos.entidades.Vacunador;
import datos.exceptions.CiudadanoRegistradoException;

import javax.ejb.Remote;

import java.util.List;
import java.util.Optional;
import java.util.Optional;


@Remote
public interface CiudadanoRepositoryRemote {
    List<Ciudadano> find();
    void save(Ciudadano ciudadano) throws CiudadanoRegistradoException;
    Ciudadano findByNombreCi(int criterio);
    Optional<Ciudadano> find(int ci);
	void saveVacunador(Vacunador vacunador);
    void deleteCiudadano(Ciudadano ciudadano);
    void ciudadanoToVacunador(String ci);
    void vacunadorToCiudadano(String ci);
    void drop();

}

