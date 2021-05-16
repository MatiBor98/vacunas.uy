package logica.creacion;

import datos.dtos.EtapaDTO;

import java.time.LocalDate;

public class EtapaDTOBuilder {
    private String vacuna;
    private LocalDate inicio;
    private LocalDate fin;
    private String planVacunacion;
    private String descripcion;
    private int id;

    public EtapaDTOBuilder setVacuna(String vacuna) {
        this.vacuna = vacuna;
        return this;
    }

    public EtapaDTOBuilder setInicio(LocalDate inicio) {
        this.inicio = inicio;
        return this;
    }

    public EtapaDTOBuilder setFin(LocalDate fin) {
        this.fin = fin;
        return this;
    }

    public EtapaDTOBuilder setPlanVacunacion(String planVacunacion) {
        this.planVacunacion = planVacunacion;
        return this;
    }

    public EtapaDTOBuilder setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public EtapaDTOBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EtapaDTO createEtapaDTO() {
        return new EtapaDTO(vacuna, inicio, fin, planVacunacion, descripcion, id);
    }
}