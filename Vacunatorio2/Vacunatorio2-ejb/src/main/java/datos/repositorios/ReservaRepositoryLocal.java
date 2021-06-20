package datos.repositorios;


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
	}
