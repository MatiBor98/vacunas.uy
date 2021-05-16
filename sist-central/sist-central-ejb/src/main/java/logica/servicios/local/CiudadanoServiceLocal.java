package logica.servicios.local;

import datos.dtos.CiudadanoDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CiudadanoServiceLocal {
    List<CiudadanoDTO> find();
    void save(CiudadanoDTO ciudadano);
    List<CiudadanoDTO> findByNombreCi(int criterio);
}
