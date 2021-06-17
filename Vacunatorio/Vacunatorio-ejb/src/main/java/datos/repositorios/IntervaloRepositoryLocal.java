package datos.repositorios;

import datos.dtos.TurnoDTO;
import datos.entidades.Intervalo;

import javax.ejb.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Local
public interface IntervaloRepositoryLocal {
    List<Intervalo> getIntervalos(LocalDateTime inicio, LocalDateTime fin, int agendaId);
    Optional<Intervalo> find(int agendaId, LocalDateTime inicio);
    List<Intervalo> findByFecha(int agendaId, LocalDateTime inicio);
    Intervalo findOrCreate(Intervalo intervalo);
	List<Intervalo> find();
	void save(Intervalo intervalo);
	void drop();
}

