package logica.servicios.local;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Ciudadano;
import datos.exceptions.CiudadanoNoEncontradoException;
import datos.exceptions.CiudadanoRegistradoException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CiudadanoServiceLocal {
    List<CiudadanoDTO> find();
    void save(CiudadanoDTO ciudadano) throws CiudadanoRegistradoException;
    CiudadanoDTO findByNombreCi(int criterio) throws CiudadanoNoEncontradoException;
    void overwriteCiudadano(CiudadanoDTO ciudadano);
    void notificar(int ci);
    void updateFirebaseTokenMovil(int ci, String firebaseToken);
}
