package laboratorio.tse;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import laboratorio.tse.entidades.Lote;
import laboratorio.tse.negocios.LoteServiceLocal;
import laboratorio.tse.repositorios.LoteRepositoryLocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.eclipse.microprofile.config.Config;

@Named("ListarBean")
@RequestScoped
public class ListarBean implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
    private int number;

    public void increment() {
        number++;
    }

    public int getNumber() {
        return number;
    }
	
	@EJB
	LoteServiceLocal service;

	@Inject
    private Config config;

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
	
	/*public void despachar(int numero) {		
		//TransportarThread transp= new TransportarThread(numero);
        //transp.run();
		try {
			//service.despacharLote(numero,LocalDate.now());
			//System.out.println("Despachado"); 
			service.transportarLoteAsync(numero, config.getValue("socioLogistico.nombre", String.class));
			//service.despacharLote(numero);
			//System.out.println("Despachado"); 
			//TimeUnit.SECONDS.sleep(5);
			//service.entregarLote(numero);
			//System.out.println("Entregado"); 
			FacesContext.getCurrentInstance().getExternalContext().redirect("transportarLotes.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}*/
	
		public void despachar(int numero) {		
		//TransportarThread transp= new TransportarThread(numero);
        //transp.run();
		try {
			String nombreSocLog = config.getValue("socioLogistico.nombre", String.class);
			LocalDate fechaDespacho = LocalDate.now();
			service.despacharLote(numero,nombreSocLog, fechaDespacho);
			System.out.println("Despachado"); 
			service.transportarLoteAsync(numero, nombreSocLog);
			FacesContext.getCurrentInstance().getExternalContext().redirect("transportarLotes.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void estado() {
		
	}
	
	public List<Lote> getList() {
		return list;
	}
	
	public String darEstado(int numeroLote) {
		if (service.find(numeroLote).get().getFechaEntrega() != null) {
			return "Entregado";
		}
		if(service.find(numeroLote).get().getFechaDespacho() != null) {
			return "Entregando";
		}
		return "Sin Entregar";
	}
	
	public void altaLote() {
	}

	
}
