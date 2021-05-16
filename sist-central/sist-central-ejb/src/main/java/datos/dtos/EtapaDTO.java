package datos.dtos;

import java.time.LocalDate;
import java.util.Objects;

public class EtapaDTO {
    private final String vacuna;
    private final LocalDate inicio;
    private final LocalDate fin;
    private final String planVacunacion;
    private final String descripcion;
    private final int id;

    public EtapaDTO(String vacuna, LocalDate inicio, LocalDate fin, String planVacunacion, String descripcion, int id) {
        this.vacuna = vacuna;
        this.inicio = inicio;
        this.fin = fin;
        this.planVacunacion = planVacunacion;
        this.descripcion = descripcion;
        this.id = id;
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
}
