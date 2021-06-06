package logica.servicios.local;

import datos.dtos.CiudadanoDTO;
import datos.dtos.VacunadorDTO;
import datos.entidades.Ciudadano;
import datos.entidades.Vacunador;
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
	Vacunador findVacunador(int ciVac);
	VacunadorDTO getVacunadorDTO(Vacunador vacunador);
}
