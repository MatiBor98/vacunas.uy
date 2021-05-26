package datos.repositorios;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Agenda;
import datos.entidades.Ciudadano;
import datos.entidades.Vacunador;

import javax.ejb.Local;
import java.util.List;


@Local
public interface CiudadanoRepositoryLocal {
    List<Ciudadano> find();
    void save(Ciudadano ciudadano);
    Ciudadano findByNombreCi(int criterio);
	void saveVacunador(Vacunador vacunador);
    public void deleteCiudadano(Ciudadano ciudadano);
    public void refreshCiudadano(Ciudadano ciudadano);
    public void ciudadanoToVacunador(String ci);
    public void vacunadorToCiudadano(String ci);

    //public CiudadanoDTO getCiudadano(int ci) throws CiudadanoNoExisteException ;
    //public List<CiudadanoDTO> getCiudadanos();
}

