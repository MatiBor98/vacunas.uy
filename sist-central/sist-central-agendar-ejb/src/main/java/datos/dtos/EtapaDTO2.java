package datos.dtos;

import java.io.Serializable;
import java.util.Objects;

public class EtapaDTO2 implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vacuna;
    private String inicio;
    private String fin;
    private String planVacunacion;
    private PlanVacunacionDTO2 planVac;
    private String descripcion;
    private int id;

    public EtapaDTO2(String vacuna, String inicio, String fin, String planVacunacion, String descripcion, int id) {
        this.vacuna = vacuna;
        this.inicio = inicio;
        this.fin = fin;
        this.planVacunacion = planVacunacion;
        this.descripcion = descripcion;
        this.id = id;
    }
    
    public EtapaDTO2(String vacuna, String inicio, String fin, PlanVacunacionDTO2 planVacunacion, String descripcion) {
        this.vacuna = vacuna;
        this.inicio = inicio;
        this.fin = fin;
        this.setPlanVac(planVacunacion);
        this.descripcion = descripcion;
    }
    
    public EtapaDTO2() {
    }

    public String getVacuna() {
        return vacuna;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
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

	public PlanVacunacionDTO2 getPlanVac() {
		return planVac;
	}

	public void setPlanVac(PlanVacunacionDTO2 planVac) {
		this.planVac = planVac;
	}
}

