package logica.servicios.local;

import datos.dtos.TurnoDTO;
import datos.entidades.Turno;

import javax.ejb.Local;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Local
public interface TurnoServiceLocal {
    List<Turno> find();
    Optional<Turno> findById(int id);
	int addTurno(String nombreTurno, String nomVac, LocalTime inicio, LocalTime fin);
	List<Turno> find(String vac, String nombreTurno);
	TurnoDTO getTurnoDTO(Turno turno);
}
