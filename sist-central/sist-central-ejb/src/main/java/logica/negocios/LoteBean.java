package logica.negocios;

import datos.dtos.LoteDTO;
import datos.entidades.*;
import datos.exceptions.VacunatorioNoExistenteException;
import datos.repositorios.*;
import logica.servicios.local.LoteServiceLocal;
import logica.servicios.local.TurnoServiceLocal;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Session Bean implementation class PuestoVacunacionBean
 */
@Stateless
public class LoteBean implements LoteServiceLocal {

	@EJB
	LoteRepositoryLocal loteRepositoryLocal;
	@EJB
	VacunatorioRepositoryLocal vacunatorioRepositoryLocal;
	@EJB
	VacunaRepositoryLocal vacunaRepositoryLocal;
	@EJB
	SocioLogisticoRepositoryLocal socioLogisticoRepositoryLocal;
	
	

	public void addLote(int dosisDisponibles, int numeroLote, String nomVac, LocalDate fechaVencimiento, String vacunaNombre, String socioLogisticoNombre) {
    	//Vacatorio vacunatorio = vacunatorioRepositoryLocal.find(nomVac).orElseThrow(VacunatorioNoExistenteException::new);
		Vacuna vacuna = vacunaRepositoryLocal.find(vacunaNombre).get();
		SocioLogistico socioLogistico = socioLogisticoRepositoryLocal.find(socioLogisticoNombre).get();
		Lote lote = new Lote(dosisDisponibles,numeroLote, fechaVencimiento, vacuna, socioLogistico);
		loteRepositoryLocal.save(lote);
		vacunatorioRepositoryLocal.addLoteAVacuantorio(lote, nomVac);
		//return turn.getId();
	}

    public Optional<Lote> findById(int numLote) {
    	return loteRepositoryLocal.findByNumLote(numLote);
    }

    public List<Lote> find() {
    	return loteRepositoryLocal.find();
    }

	@Override
	public void despacharLote(int numeroLote, String socioLogistico, LocalDate fechaDespacho) {
		loteRepositoryLocal.despacharLote(numeroLote, socioLogistico,fechaDespacho);
	}

	@Override
	public void entregarLote(int numeroLote, String socioLogistico, LocalDate fechaEntrega) {
		loteRepositoryLocal.entregarLote(numeroLote, socioLogistico,fechaEntrega);
	}

	/*@Override
	public List<Lote> find(String vac, String nombreLote) {
		return loteRepositoryLocal.find(vac, nombreLote);

	}*/
	

	/**
     * Default constructor.
     */
    public LoteBean() {
        // TODO Auto-generated constructor stub
    }




}
