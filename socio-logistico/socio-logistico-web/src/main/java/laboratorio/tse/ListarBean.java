package laboratorio.tse;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import laboratorio.tse.entidades.Lote;
import laboratorio.tse.negocios.LoteServiceLocal;
import laboratorio.tse.repositorios.LoteRepositoryLocal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

@Named("ListarBean")
@ViewScoped
public class ListarBean implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	LoteRepositoryLocal service;




	private List<Lote> list; 
	
	public ListarBean() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {		
		//Lote lote = new Lote(10, 1, LocalDate.now(), "Pfizer"); 
		//service.save(lote);
		list = service.find();
	}
	
	public void despachar(int numero) {		
		//Lote lote = new Lote(10, 1, LocalDate.now(), "Pfizer"); 
		//service.save(lote);
		service.despacharLote(numero);
	}
	
	
	public List<Lote> getList() {
		return list;
	}
	

	
	public void altaLote() {
	}
	

	
}
