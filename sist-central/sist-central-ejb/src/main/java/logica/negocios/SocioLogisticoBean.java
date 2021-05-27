package logica.negocios;

import datos.entidades.*;
import datos.exceptions.PuestoVacunacionNoExistenteException;
import datos.exceptions.VacunatorioNoExistenteException;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
import datos.repositorios.SocioLogisticoRepositoryLocal;
import datos.repositorios.TurnoRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryLocal;
import logica.servicios.local.SocioLogisticoControllerLocal;
import logica.servicios.local.VacunatorioControllerLocal;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Session Bean implementation class VacunatorioBean
 */
@Stateless
@LocalBean
public class SocioLogisticoBean  implements SocioLogisticoControllerLocal {



	@EJB
	private SocioLogisticoRepositoryLocal socioLogisticoRepositoryLocal;

    /**
     * Default constructor.
     */
    public SocioLogisticoBean() {
    }

    public void addSocioLogistico(String nombre) {
    	SocioLogistico soc = new SocioLogistico(nombre,false);
		socioLogisticoRepositoryLocal.save(soc);
    }
    
    public Optional<SocioLogistico> find(String nombre) {
    	return socioLogisticoRepositoryLocal.find(nombre);
    }

	public List<SocioLogistico> find() {
		return socioLogisticoRepositoryLocal.find();
	}

	public void habilitarSocioLogistico(String nombre) {
		socioLogisticoRepositoryLocal.habilitar(nombre);
	}
}
