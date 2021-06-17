package logica.negocios;

import datos.entidades.*;
import datos.repositorios.SocioLogisticoRepositoryLocal;
import logica.servicios.local.SocioLogisticoControllerLocal;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
	private String property = "0";


	/**
     * Default constructor.
     */
    public SocioLogisticoBean() {
    }

    public void addSocioLogistico(String nombre) {
    	SocioLogistico soc = new SocioLogistico(nombre, null, false);
		socioLogisticoRepositoryLocal.save(soc);
    }
    
    public Optional<SocioLogistico> find(String nombre) {
    	return socioLogisticoRepositoryLocal.find(nombre);
    }

	public List<SocioLogistico> find() {
		return socioLogisticoRepositoryLocal.find();
	}
	public List<SocioLogistico> findHabilitados() {
		return socioLogisticoRepositoryLocal.findHabilitados();
	}

	public void habilitarSocioLogistico(String nombre) {
		socioLogisticoRepositoryLocal.habilitar(nombre);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Override
	public List<Lote> getLotes(String nomSocLog) {
		return socioLogisticoRepositoryLocal.getLotes(nomSocLog);
	}
}
