package datos.repositorios;

import datos.dtos.ReservaDTO;
import datos.entidades.Reserva;
import datos.entidades.ReservaDomicilio;
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
	
	public List<Reserva> findReservasDomicilio(int offset, int limit);

	
    /*public void saveReservaDomicilio(ReservaDomicilio reserva);
    public List<ReservaDomicilio> findReservasADomicilioCiudadano(int offset, int limit, int ci);
    public List<ReservaDomicilio> findReservasADomicilio(int offset, int limit);
	*/
}
