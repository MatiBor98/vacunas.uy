package logica.negocios;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import datos.entidades.Departamento;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Vacunatorio;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryLocal;
import exceptions.PuestoVacunacionNoExistenteException;
import exceptions.VacunatorioNoExistenteException;
import logica.servicios.local.VacunatorioControllerLocal;
import logica.servicios.remote.VacunatorioControllerRemote;

/**
 * Session Bean implementation class VacunatorioBean
 */
@Stateless
@LocalBean
public class VacunatorioBean implements VacunatorioControllerRemote, VacunatorioControllerLocal {

	@EJB
	VacunatorioRepositoryLocal vacunatorioRepositoryLocal;
	
	@EJB
	PuestoVacunacionRepositoryLocal puestoVacunacionRepositoryLocal;
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
    public List<Vacunatorio> find() {
    	return vacunatorioRepositoryLocal.find();
    }

	public void addPuestoAlVacunatorio(String nombreVacunatorio, long idPuesto) {
		Vacunatorio vac = vacunatorioRepositoryLocal.find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
		PuestoVacunacion puesto = puestoVacunacionRepositoryLocal.find(idPuesto).orElseThrow(PuestoVacunacionNoExistenteException::new);
		vac.getPuestosVacunacion().add(puesto);
	}
    
}
