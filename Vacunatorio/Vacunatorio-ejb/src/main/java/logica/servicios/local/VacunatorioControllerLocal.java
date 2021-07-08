package logica.servicios.local;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import datos.entidades.Departamento;
import datos.entidades.Estado;
import datos.entidades.Lote;
import datos.entidades.Reserva;
import datos.entidades.ReservaConfirmada;
import datos.entidades.Vacunatorio;
import logica.schedule.DatosVacunatorio;

@Local
public interface VacunatorioControllerLocal {
	void addVacunatorio(String nombre, String ciudad, String direccion, Departamento departamento);
	Optional<Vacunatorio> find(String nombre);
	Optional<Vacunatorio> findWithEverything(String nombre);
	List<Vacunatorio> find();
	void addPuestoAlVacunatorio(String nombreVacunaotorio, int idPuesto);
	int addTurno(String nombreTurno, LocalTime inicio, LocalTime fin, String nombreVacunatorio);
	List<String> getNombresDepartamentos();
	List<Vacunatorio> findByDepartamento(Departamento dep, int primerResultado, int maximosResultados); 
	List<Vacunatorio> findByDepartamento(Departamento dep);
	List<Reserva> findReservas();
	ReservaConfirmada findReservaConfirmada(int codigo);
	void saveReservaConfirmada(ReservaConfirmada resConf);
	List<Lote> findLotes();
	void actualizar();
	List<Reserva> listarVacunados(int offset, int limit, String busqueda);
	List<Reserva> listarPendientes(int offset, int limit, String busqueda);
	List<Reserva> listarCancelados(int offset, int limit, String busqueda);
	List<Reserva> listarConfirmados(int offset, int limit, String busqueda);
	Long calcularCantidad(Estado vacunado);
	Long calcularCantidadConfirmadas();
}
