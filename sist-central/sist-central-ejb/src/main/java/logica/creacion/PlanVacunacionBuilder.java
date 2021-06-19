package logica.creacion;

import datos.entidades.Enfermedad;
import datos.entidades.PlanVacunacion;

import java.time.LocalDate;

public class PlanVacunacionBuilder {
    private String nombre;
    private LocalDate inicio;
    private LocalDate fin;
    private Enfermedad enfermedad;

    public PlanVacunacionBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public PlanVacunacionBuilder setInicio(LocalDate date) {
        this.inicio = date;
        return this;
    }

    public PlanVacunacionBuilder setFin(LocalDate fin) {
        this.fin = fin;
        return this;
    }

    public PlanVacunacionBuilder setEnfermedad(Enfermedad enfermedad) {
        this.enfermedad = enfermedad;
        return this;
    }

    public PlanVacunacion createPlanVacunacion() {
        return new PlanVacunacion(nombre, inicio, fin, enfermedad);
    }
}