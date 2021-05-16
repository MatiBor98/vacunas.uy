package logica.creacion;

import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;
import datos.entidades.RestriccionEtapa;
import datos.entidades.Vacuna;

import java.time.LocalDate;

public class EtapaBuilder {
    private int id;
    private RestriccionEtapa restricciones;
    private String descripcion;
    private LocalDate inicio;
    private LocalDate fin;
    private Vacuna vacuna;
    private PlanVacunacion planVacunacion;

    public EtapaBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EtapaBuilder setRestricciones(RestriccionEtapa restricciones) {
        this.restricciones = restricciones;
        return this;
    }

    public EtapaBuilder setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public EtapaBuilder setInicio(LocalDate inicio) {
        this.inicio = inicio;
        return this;
    }

    public EtapaBuilder setFin(LocalDate fin) {
        this.fin = fin;
        return this;
    }

    public EtapaBuilder setVacuna(Vacuna vacuna) {
        this.vacuna = vacuna;
        return this;
    }

    public EtapaBuilder setPlanVacunacion(PlanVacunacion planVacunacion) {
        this.planVacunacion = planVacunacion;
        return this;
    }

    public Etapa createEtapa() {
        return new Etapa(id, restricciones, descripcion, inicio, fin, vacuna, planVacunacion);
    }
}