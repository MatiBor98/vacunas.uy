package datos.repositorios;


import datos.entidades.Estado;
import datos.entidades.Reserva;
import datos.entidades.ReservaConfirmada;

import java.util.List;

import javax.ejb.Local;


@Local
public interface ReservaRepositoryLocal {
    void save(Reserva reserva);
    boolean existeReservaPendienteByCiudadanoEnfermedad(int ci, String enfermedad);
    void drop();
    List<Reserva> find();
	ReservaConfirmada findReservaConfirmada(int codigo);
	void saveReservaConfrimada(ReservaConfirmada resConf);
	void dropReservasConfirmadas();
	List<ReservaConfirmada> findReservasConfirmadas();
	List<Reserva> listarVacunados(int offset, int limit, String busqueda);
	List<Reserva> findByEstado(Estado vacunado);
	List<Reserva> listarPendientes(int offset, int limit, String busqueda);
	List<Reserva> listarCancelados(int offset, int limit, String busqueda);
	List<ReservaConfirmada> listarConfirmados(int offset, int limit, String busqueda);
	List<ReservaConfirmada> findConfrimados();
	Reserva findReserva(int codigo);
	}
