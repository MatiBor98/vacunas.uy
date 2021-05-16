package logica.creacion;

import datos.dtos.PlanVacunacionDTO;

import java.time.LocalDate;

public class PlanVacunacionDTOBuilder {
    private String nombre;
    private LocalDate inicio;
    private LocalDate fin;
    private String enfermedad;

    public PlanVacunacionDTOBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public PlanVacunacionDTOBuilder setInicio(LocalDate inicio) {
        this.inicio = inicio;
        return this;
    }

    public PlanVacunacionDTOBuilder setFin(LocalDate fin) {
        this.fin = fin;
        return this;
    }

    public PlanVacunacionDTOBuilder setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
        return this;
    }

    public PlanVacunacionDTO createPlanVacunacionDTO() {
        return new PlanVacunacionDTO(nombre, inicio, fin, enfermedad);
    }
}