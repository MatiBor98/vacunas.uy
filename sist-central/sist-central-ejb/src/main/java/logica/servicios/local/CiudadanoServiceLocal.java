package logica.servicios.local;

import datos.dtos.CiudadanoDTO;
import datos.dtos.VacunadorDTO;
import datos.entidades.Ciudadano;
import datos.entidades.Vacunador;
import datos.exceptions.CiudadanoNoEncontradoException;
import datos.exceptions.CiudadanoRegistradoException;
import plataformainteroperabilidad.Sexo;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;

@Local
public interface CiudadanoServiceLocal {
    List<CiudadanoDTO> find();
    void save(CiudadanoDTO ciudadano) throws CiudadanoRegistradoException;
    List<CiudadanoDTO> findTokenNotNull();
    CiudadanoDTO findByNombreCi(int criterio) throws CiudadanoNoEncontradoException;
    void overwriteCiudadano(CiudadanoDTO ciudadano);
    void updateFirebaseTokenMovil(int ci, String firebaseToken);
    void notificar(String firebaseToken, String titulo, String cuerpo);
	Vacunador findVacunador(int ciVac);
	VacunadorDTO getVacunadorDTO(Vacunador vacunador);
    void notificarTodosLosUsuariosMoviles(String titulo , String cuerpo);
    void setSexoFechanacimiento(Integer ci, Sexo sexo, LocalDate fechaNacimiento, Trabajo trabajo);
}
