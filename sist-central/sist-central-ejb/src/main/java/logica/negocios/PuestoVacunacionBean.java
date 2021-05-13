package logica.negocios;

import java.time.LocalTime;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import datos.entidades.PuestoVacunacion;
import datos.entidades.Turno;
import datos.entidades.Vacunatorio;
import datos.exceptions.VacunatorioNoExistenteException;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryLocal;
import logica.servicios.local.PuestoVacunacionBeanLocal;

/**
 * Session Bean implementation class PuestoVacunacionBean
 */
@Stateless
@LocalBean
public class PuestoVacunacionBean implements  PuestoVacunacionBeanLocal {

	@EJB
	PuestoVacunacionRepositoryLocal puestoVacunacionRepositoryLocal;
	@EJB
	VacunatorioRepositoryLocal vacunatorioRepositoryLocal;
	
    @Override
	public long addPuestoVacunacion(String nombrePuesto, String nombreVacunatorio) {
    	Vacunatorio vacunatorio = vacunatorioRepositoryLocal.find(nombreVacunatorio).orElseThrow(VacunatorioNoExistenteException::new);
		PuestoVacunacion puesto = new PuestoVacunacion(vacunatorio, nombrePuesto);
		puestoVacunacionRepositoryLocal.save(puesto);
		return puesto.getId();
	}

	/**
     * Default constructor. 
     */
    public PuestoVacunacionBean() {
        // TODO Auto-generated constructor stub
    }



}
