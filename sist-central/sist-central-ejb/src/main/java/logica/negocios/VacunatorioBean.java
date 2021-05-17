package logica.negocios;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import datos.entidades.Departamento;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Turno;
import datos.entidades.Vacunatorio;
import datos.exceptions.PuestoVacunacionNoExistenteException;
import datos.exceptions.VacunatorioNoExistenteException;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
import datos.repositorios.TurnoRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryLocal;
import logica.servicios.local.VacunatorioControllerLocal;

/**
 * Session Bean implementation class VacunatorioBean
 */
@Stateless
@LocalBean
public class VacunatorioBean implements  VacunatorioControllerLocal {

	

	@EJB
	VacunatorioRepositoryLocal vacunatorioRepositoryLocal;
	
	@EJB
	PuestoVacunacionRepositoryLocal puestoVacunacionRepositoryLocal;
	
	@EJB
	TurnoRepositoryLocal turnoRepositoryLocal;
    /**
     * Default constructor. 
     */
    public VacunatorioBean() {
    }

    public void addVacunatorio(String nombre, String ciudad, String direccion, Departamento departamento) {
    	Vacunatorio vac = new Vacunatorio(nombre, ciudad, direccion, departamento);
    	vacunatorioRepositoryLocal.save(vac);
    }
    
    public Optional<Vacunatorio> find(String nombre) {
    	return vacunatorioRepositoryLocal.find(nombre);

    }
    
	public Optional<Vacunatorio> findWithEverything(String nombre) {
    	return vacunatorioRepositoryLocal.findWithEverything(nombre);

	}
    
	
	
	
    public List<Vacunatorio> find() {
    	return vacunatorioRepositoryLocal.find();
    }

    @Override
    public List<Vacunatorio> findByDepartamento(Departamento departamento) {
    	return vacunatorioRepositoryLocal.findByDepartamento(departamento);
	}

	public void addPuestoAlVacunatorio(String nombreVacunatorio, int idPuesto) {
		Vacunatorio vac = vacunatorioRepositoryLocal.find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
		PuestoVacunacion puesto = puestoVacunacionRepositoryLocal.find(idPuesto).orElseThrow(PuestoVacunacionNoExistenteException::new);
		vac.getPuestosVacunacion().add(puesto);
	}
    
	public List<Vacunatorio> findByPage(int primerResultado, int limiteResultados) {
		return vacunatorioRepositoryLocal.find(primerResultado, limiteResultados);
		
	}
	
	
	
	@Override
	public int addTurno(String nombreTurno, LocalTime inicio, LocalTime fin, String nombreVacunatorio) {
		Vacunatorio vacunatorio = vacunatorioRepositoryLocal.find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
		Turno turno = new Turno(nombreTurno, inicio, fin, vacunatorio);
		turnoRepositoryLocal.save(turno);
		return turno.getId();
	}
	public List<Vacunatorio> findByDepartamento(Departamento dep, int primerResultado, int maximosResultados) {
		return vacunatorioRepositoryLocal.findByDepartamento(dep, primerResultado, maximosResultados);
	}
	
	public List<Vacunatorio> findByDepartamento(Departamento dep) {
		return vacunatorioRepositoryLocal.findByDepartamento(dep);
	}
	public List<String> getNombresDepartamentos() {
		List<String> res = new ArrayList<>();
		Departamento[] deps = Departamento.values();
		for(Departamento dep:deps) {
			res.add(dep.toString());
		}
		return res;
	}
}
