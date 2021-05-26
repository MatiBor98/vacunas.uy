package logica.servicios.local;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Ciudadano;
import datos.exceptions.CiudadanoNoEncontradoException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CiudadanoServiceLocal {
    List<CiudadanoDTO> find();
    void save(CiudadanoDTO ciudadano);
    CiudadanoDTO findByNombreCi(int criterio) throws CiudadanoNoEncontradoException;
    public void overwriteCiudadano(CiudadanoDTO ciudadano);

}
