package datos.repositorios;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Agenda;
import datos.entidades.Ciudadano;

import javax.ejb.Local;
import java.util.List;


@Local
public interface CiudadanoRepositoryLocal {
    List<Ciudadano> find();
    void save(Ciudadano ciudadano);
    List<Ciudadano> findByNombreCi(int criterio);

    //public CiudadanoDTO getCiudadano(int ci) throws CiudadanoNoExisteException ;
    //public List<CiudadanoDTO> getCiudadanos();
}

