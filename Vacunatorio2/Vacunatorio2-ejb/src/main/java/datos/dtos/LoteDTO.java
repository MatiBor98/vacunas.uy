package datos.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


public class LoteDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int dosisDisponibles;
	private Integer numeroLote;
	private String fechaVencimiento;
	private VacunaDTO vacuna;
	private String fechaEntrega;
	private String fechaDespacho;
	private SocioLogisticoDTO socioLogistico;
    public LoteDTO() {
        super();
    }
    
    public LoteDTO(int dosisDisponibles, int numeroLote, String fechaVencimiento, VacunaDTO vacuna, SocioLogisticoDTO socioLogistico) {
    	super();
    	this.dosisDisponibles = dosisDisponibles;
    	this.numeroLote = numeroLote;
    	this.fechaVencimiento = fechaVencimiento;
    	this.vacuna = vacuna;
    	this.socioLogistico = socioLogistico;
    	this.fechaEntrega = null;
    	this.fechaDespacho = null;
    }
    
    public LoteDTO(int dosisDisponibles, int numeroLote, String fechaVencimiento, VacunaDTO vacuna, String fechaEntrega, String fechaDespacho) {
    	super();
    	this.dosisDisponibles = dosisDisponibles;
    	this.numeroLote = numeroLote;
    	this.fechaVencimiento = fechaVencimiento;
    	this.vacuna = vacuna;
    	this.socioLogistico = null;
    	this.fechaEntrega = fechaEntrega;
    	this.fechaDespacho = fechaDespacho;
    }
    
    public int getDosisDisponibles() {
    	return dosisDisponibles;
    }
    
    public Integer getNumeroLote() {
    	return numeroLote;
    }
    
    public String getFechaVencimiento() {
    	return fechaVencimiento;
    }

    
    public VacunaDTO getVacuna() {
    	return vacuna;
    }

	public String getFechaEntrega(){
		return fechaEntrega;
	}

	public String getFechaDespacho(){
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

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public void setVacuna(VacunaDTO vacuna) {
		this.vacuna = vacuna;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public void setFechaDespacho(String fechaDespacho) {
		this.fechaDespacho = fechaDespacho;
	}
	
	public void setSocioLogistico(SocioLogisticoDTO socioLogistico){
		this.socioLogistico = socioLogistico;
	}
	
	/*public void setVacunatorio(Vacunatorio vacunatorio) {
		this.vacunatorio = vacunatorio;
	}*/

}
