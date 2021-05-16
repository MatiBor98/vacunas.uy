package logica.servicios.local;

import datos.dtos.PlanVacunacionDTO;

import javax.ejb.Local;

@Local
public interface PlanVacunacionServiceLocal {
    PlanVacunacionDTO save(PlanVacunacionDTO planVacunacionDTO);
}
