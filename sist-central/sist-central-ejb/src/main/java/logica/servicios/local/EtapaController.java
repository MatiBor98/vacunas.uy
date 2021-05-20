package logica.servicios.local;

import datos.dtos.EtapaDTO;
import datos.entidades.Trabajos;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface EtapaController {
    EtapaDTO save(EtapaDTO etapaDTO);
    Optional<EtapaDTO> find(int id);
    List<EtapaDTO> find(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos);

    boolean habilidadoCiudadano(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos);
}
