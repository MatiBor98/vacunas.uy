package negocio;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import datos.entidades.Departamento;
import datos.entidades.Vacunatorio;
import datos.repositorios.VacunatorioRepositoryLocal;

/**
 * Session Bean implementation class VacunatorioController
 */
@Stateless
@LocalBean
public class VacunatorioController implements VacunatorioControllerRemote, VacunatorioControllerLocal {

	@EJB
	VacunatorioRepositoryLocal vacunatorioRepositoryLocal;
    /**
     * Default constructor. 
     */
    public VacunatorioController() {
    }

    public void addVacunatorio(String nombre, String ciudad, String direccion, Departamento departamento) {
    	Vacunatorio vac = new Vacunatorio(nombre, ciudad, direccion, departamento);
    	vacunatorioRepositoryLocal.save(vac);
    }
    
    public Optional<Vacunatorio> getVacunatorio(String nombre) {
    	return vacunatorioRepositoryLocal.find(nombre);
    }
    public List<Vacunatorio> getAllVacunatorio() {
    	return vacunatorioRepositoryLocal.find();
    }
}
