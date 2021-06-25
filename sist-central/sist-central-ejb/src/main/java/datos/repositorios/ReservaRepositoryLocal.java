package datos.repositorios;

import datos.dtos.ReservaDTO;
import datos.entidades.Reserva;
import datos.entidades.Vacunatorio;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ReservaRepositoryLocal {

    public void save(Reserva reserva);
    public boolean existeReservaPendienteByCiudadanoEnfermedad(int ci, String enfermedad);
    public List<Reserva> findReservasTomorrow();
	public List<Reserva> findReservasVacunatorio(Vacunatorio vac);
	public List<Reserva> find();
	public Reserva findByID(int codigo);
}
