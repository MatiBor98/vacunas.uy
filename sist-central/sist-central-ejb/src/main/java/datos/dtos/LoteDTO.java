package datos.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoteDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int dosisDisponibles;
	private Integer numeroLote;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  
	private Date fechaVencimiento;
	private VacunaDTO vacuna;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") 
	private Date fechaEntrega;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") 
	private Date fechaDespacho;
	private SocioLogisticoDTO socioLogistico;
    public LoteDTO(){
    }
    
    public LoteDTO(int dosisDisponibles, int numeroLote, Date fechaVencimiento, VacunaDTO vacuna, SocioLogisticoDTO socioLogistico) {
    	super();
    	this.dosisDisponibles = dosisDisponibles;
    	this.numeroLote = numeroLote;
    	this.fechaVencimiento = fechaVencimiento;
    	this.vacuna = vacuna;
    	this.socioLogistico = socioLogistico;
    	this.fechaEntrega = null;
    	this.fechaDespacho = null;
    }
    
    public LoteDTO(int dosisDisponibles, int numeroLote, Date fechaVencimiento, VacunaDTO vacuna, Date fechaEntrega, Date fechaDespacho) {
    	super();
    	this.dosisDisponibles = dosisDisponibles;
    	this.numeroLote = numeroLote;
    	this.fechaVencimiento = fechaVencimiento;
    	this.vacuna = vacuna;
    	this.socioLogistico = null;;
    	this.fechaEntrega = fechaEntrega;
    	this.fechaDespacho = fechaDespacho;
    }
    
    public int getDosisDisponibles() {
    	return dosisDisponibles;
    }
    
    public Integer getNumeroLote() {
    	return numeroLote;
    }
    
    public Date getFechaVencimiento() {
    	return fechaVencimiento;
    }

    
    public VacunaDTO getVacuna() {
    	return vacuna;
    }

	public Date getFechaEntrega(){
		return fechaEntrega;
	}

	public Date getFechaDespacho(){
		return fechaDespacho;
	}
	
	/*public Vacunatorio getVacunatorio(){
		return vacunatorio;
	}*/
	
	public SocioLogisticoDTO getSocioLogistico(){
		return socioLogistico;
	}


	public void setDosisDisponibles(int dosisDisponibles) {
		this.dosisDisponibles = dosisDisponibles;
	}

	public void setNumeroLote(Integer numeroLote) {
		this.numeroLote = numeroLote;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public void setVacuna(VacunaDTO vacuna) {
		this.vacuna = vacuna;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public void setFechaDespacho(Date fechaDespacho) {
		this.fechaDespacho = fechaDespacho;
	}
	
	public void setSocioLogistico(SocioLogisticoDTO socioLogistico){
		this.socioLogistico = socioLogistico;
	}
	
	/*public void setVacunatorio(Vacunatorio vacunatorio) {
		this.vacunatorio = vacunatorio;
	}*/

}
