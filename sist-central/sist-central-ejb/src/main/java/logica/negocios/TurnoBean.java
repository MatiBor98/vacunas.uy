package logica.negocios;

import datos.dtos.TurnoDTO;
import datos.entidades.Turno;
import datos.entidades.Vacunatorio;
import datos.exceptions.VacunatorioNoExistenteException;
import datos.repositorios.TurnoRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryLocal;
import logica.servicios.local.TurnoServiceLocal;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Session Bean implementation class PuestoVacunacionBean
 */
@Stateless
@LocalBean
public class TurnoBean implements  TurnoServiceLocal {

	@EJB
	TurnoRepositoryLocal turnoRepositoryLocal;
	@EJB
	VacunatorioRepositoryLocal vacunatorioRepositoryLocal;
	
	public int addTurno(String nombreTurno, String nomVac, LocalTime inicio, LocalTime fin) {
    	Vacunatorio vacunatorio = vacunatorioRepositoryLocal.find(nomVac).orElseThrow(VacunatorioNoExistenteException::new);
		Turno turn = new Turno(nombreTurno, inicio, fin, vacunatorio);
		turnoRepositoryLocal.save(turn);
		return turn.getId();
	}
    
    public Optional<Turno> findById(int id) {
    	return turnoRepositoryLocal.findById(id);
    }
    
    public List<Turno> find() {
    	return turnoRepositoryLocal.find();
    }
    
    public List<Turno> find(String vac, String nombreTurno) {
    	return turnoRepositoryLocal.find(vac, nombreTurno);
    }
	/**
     * Default constructor. 
     */
    public TurnoBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public TurnoDTO getTurnoDTO(Turno turno) {
		TurnoDTO res = new TurnoDTO(turno.getNombre(), turno.getInicio().toString(), turno.getFin().toString(), turno.getVacunatorio().getNombre());
		return res;
	}



}
