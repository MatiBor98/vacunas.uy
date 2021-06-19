package logica.negocios;

import datos.dtos.LoteDTO;
import datos.dtos.VacunaDTO;
import datos.entidades.Lote;
import datos.entidades.SocioLogistico;
import datos.entidades.Vacuna;
import datos.repositorios.LoteRepositoryLocal;
import datos.repositorios.SocioLogisticoRepositoryLocal;
import datos.repositorios.VacunaRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryLocal;
import logica.servicios.local.LoteServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

	@Override
	public LoteDTO getLoteDTO(Lote lote) {
		VacunaDTO vacDTO = new VacunaDTO(null, null, lote.getVacuna().getNombre(), lote.getVacuna().getCantDosis(), lote.getVacuna().getInmunidadMeses(), lote.getVacuna().getDosisSeparacionDias());
		LoteDTO res = new LoteDTO(lote.getDosisDisponibles(), lote.getNumeroLote(), Date.from(lote.getFechaVencimiento().atStartOfDay(ZoneId.systemDefault()).toInstant()), vacDTO, Date.from(lote.getFechaEntrega().atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(lote.getFechaDespacho().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		return res;
	}

	@Override
	public void decrementar(int loteID) {
		Lote lote = findById(loteID).get();
		if((lote != null) && (lote.getDosisDisponibles() > 0)) {
			lote.setDosisDisponibles(lote.getDosisDisponibles() - 1);
		}
		
	}





}
