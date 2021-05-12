package logica.negocios;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import datos.entidades.Departamento;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Vacunatorio;
import datos.exceptions.PuestoVacunacionNoExistenteException;
import datos.exceptions.VacunatorioNoExistenteException;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
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
