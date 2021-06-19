package logica.servicios.local;

import datos.dtos.PlanVacunacionDTO;
import datos.dtos.PlanVacunacionDTO2;
import datos.entidades.Enfermedad;
import datos.entidades.PlanVacunacion;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Local
public interface PlanVacunacionServiceLocal {
    PlanVacunacionDTO save(PlanVacunacionDTO planVacunacionDTO);
    Optional<PlanVacunacion> find(String nombre);
    void save(String nombre, LocalDate inicio, LocalDate fin, Enfermedad enfermedad);
    List<PlanVacunacion> find();
	Date getFechaFin(String planVac);
	PlanVacunacionDTO getPVacDTO(PlanVacunacion planVacunacion);
	PlanVacunacionDTO2 getPVacDTO2(PlanVacunacion planVacunacion);
}
