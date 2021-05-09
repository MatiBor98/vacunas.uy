package datos.entidades;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Lote{
	
	@Column(nullable=false)
	private int dosisDisponibles;
	@Id
	private Integer numeroLote;
	@Column(nullable=false)
	private LocalDate fechaVencimiento;
	@ManyToOne
	private Vacuna vacuna;
	@Column
	private LocalDate fechaEntrega;
	@Column
	private LocalDate fechaDespacho;
	@ManyToOne
	@JoinColumn(name="vacunatorio_id", nullable=false)
	private Vacunatorio vacunatorio;
		
    /**
     * Default constructor. 
     */
    public Lote() {
        super();
    }
    
    public Lote(int dosisDisponibles, int numeroLote, LocalDate fechaVencimiento, Vacuna vacuna) {
    	super();
    	this.dosisDisponibles = dosisDisponibles;
    	this.numeroLote = numeroLote;
    	this.fechaVencimiento = fechaVencimiento;
    	this.vacuna = vacuna;
    			
    }
    
    public int getDosisDisponibles() {
    	return dosisDisponibles;
    }
    
    public Integer getNumeroLote() {
    	return numeroLote;
    }
    
    public LocalDate getFechaVencimiento() {
    	return fechaVencimiento;
    }

    
    public Vacuna getVacuna() {
    	return vacuna;
    }

	public LocalDate getFechaEntrega(){
		return fechaEntrega;
	}

	public LocalDate getFechaDespacho(){
		return fechaDespacho;
	}
	
	public Vacunatorio getVacunatorio(){
		return vacunatorio;
	}


	public void setDosisDisponibles(int dosisDisponibles) {
		this.dosisDisponibles = dosisDisponibles;
	}

	public void setNumeroLote(Integer numeroLote) {
		this.numeroLote = numeroLote;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public void setVacuna(Vacuna vacuna) {
		this.vacuna = vacuna;
	}

	public void setFechaEntrega(LocalDate fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public void setFechaDespacho(LocalDate fechaDespacho) {
		this.fechaDespacho = fechaDespacho;
	}
	
	public void setVacunatorio(Vacunatorio vacunatorio) {
		this.vacunatorio = vacunatorio;
	}

}
