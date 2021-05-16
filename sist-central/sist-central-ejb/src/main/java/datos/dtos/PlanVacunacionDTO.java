package datos.dtos;

import java.time.LocalDate;

public class PlanVacunacionDTO {
    private final String nombre;
    private final LocalDate inicio;
    private final LocalDate fin;
    private final String enfermedad;

    public PlanVacunacionDTO(String nombre, LocalDate inicio, LocalDate fin, String enfermedad) {
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
        this.enfermedad = enfermedad;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public String getEnfermedad() {
        return enfermedad;
    }
}
