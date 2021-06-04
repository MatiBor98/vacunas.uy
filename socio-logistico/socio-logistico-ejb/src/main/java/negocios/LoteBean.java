package negocios;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import laboratorio.tse.entidades.Lote;
import laboratorio.tse.repositorios.LoteRepositoryLocal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Session Bean implementation class PuestoVacunacionBean
 */
@Stateless
@LocalBean
public class LoteBean implements LoteServiceLocal {

	@EJB
	LoteRepositoryLocal loteRepositoryLocal;
	
	public void addLote(int dosisDisponibles, int numeroLote, String nomVac, LocalDate fechaVencimiento, String vacunaNombre) {
		Lote lote = new Lote(dosisDisponibles,numeroLote, fechaVencimiento, nomVac);
		loteRepositoryLocal.save(lote);
	}


    public List<Lote> find() {
    	return loteRepositoryLocal.find();
    }



	/**
     * Default constructor.
     */
    public LoteBean() {
        // TODO Auto-generated constructor stub
    }



}

