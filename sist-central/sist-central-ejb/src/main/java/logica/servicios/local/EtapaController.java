package logica.servicios.local;

import datos.dtos.EtapaDTO;
import datos.dtos.EtapaDTO2;
import datos.entidades.Etapa;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Local
public interface EtapaController {
    EtapaDTO save(EtapaDTO etapaDTO);
    Optional<EtapaDTO> find(int id);
    List<EtapaDTO> find(String nombreEnfermedad, int edadCiudadano, Trabajo trabajos);
    void save(String nomVac, LocalDate inicio, LocalDate fin, String planVacunacion, String descripcion, List<Trabajo> trabajos, int edadMin, int edadMax);
	List<String> getNombresTrabajos();
    boolean existeEtapaParaCiudadano(String nombreEnfermedad, int edadCiudadano, Trabajo trabajos);
	EtapaDTO getEtapaDTO(Etapa etapa);
	EtapaDTO2 getEtapaDTO2(Etapa etapa);
}
