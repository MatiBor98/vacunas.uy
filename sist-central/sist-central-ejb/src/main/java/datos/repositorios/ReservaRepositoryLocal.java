package datos.repositorios;

import datos.entidades.Reserva;
import datos.entidades.Vacunatorio;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ReservaRepositoryLocal {

    public void save(Reserva reserva);
    public boolean existeReservaPendienteByCiudadanoEnfermedad(int ci, String enfermedad);
    public List<Reserva> findReservasTomorrow();
	public List<Reserva> findReservasVacunatorio(Vacunatorio vac);
	public List<Reserva> find();
	public Reserva findByID(int codigo);
}
