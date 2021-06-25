package datos.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EtapaDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vacuna;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-M-yyyy")  
    private LocalDate inicio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-M-yyyy")  
    private LocalDate fin;
    private String planVacunacion;
    private PlanVacunacionDTO planVac;
    private String descripcion;
    private int id;

    public EtapaDTO(String vacuna, LocalDate inicio, LocalDate fin, String planVacunacion, String descripcion, int id) {
        this.vacuna = vacuna;
        this.inicio = inicio;
        this.fin = fin;
        this.planVacunacion = planVacunacion;
        this.descripcion = descripcion;
        this.id = id;
    }
    
    public EtapaDTO(String vacuna, LocalDate inicio, LocalDate fin, PlanVacunacionDTO planVacunacion, String descripcion) {
        this.vacuna = vacuna;
        this.inicio = inicio;
        this.fin = fin;
        this.planVac = planVacunacion;
        this.descripcion = descripcion;
    }
    
    public EtapaDTO() {
    	
    }

    public String getVacuna() {
        return vacuna;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public String getPlanVacunacion() {
        return planVacunacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EtapaDTO etapaDTO = (EtapaDTO) o;
        return getId() == etapaDTO.getId() && getVacuna().equals(etapaDTO.getVacuna()) &&
                getInicio().equals(etapaDTO.getInicio()) && getFin().equals(etapaDTO.getFin()) &&
                getPlanVacunacion().equals(etapaDTO.getPlanVacunacion()) &&
                getDescripcion().equals(etapaDTO.getDescripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVacuna(), getInicio(), getFin(), getPlanVacunacion(), getDescripcion(), getId());
    }

	public PlanVacunacionDTO getPlanVac() {
		return planVac;
	}
}
