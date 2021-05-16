package datos.entidades;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Embeddable
public class RestriccionEtapa {
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Trabajos> filtroEmpleoEn = Collections.emptyList();
    private Integer mayorIgual;
    private Integer menorIgual;

    public List<Trabajos> getFiltroEmpleoEn() {
        return filtroEmpleoEn;
    }

    public void setFiltroEmpleoEn(List<Trabajos> filtroEmpleoEn) {
        this.filtroEmpleoEn = filtroEmpleoEn;
    }

    public Integer getMayorIgual() {
        return mayorIgual;
    }

    public void setMayorIgual(Integer mayorIgual) {
        this.mayorIgual = mayorIgual;
    }

    public Integer getMenorIgual() {
        return menorIgual;
    }

    public void setMenorIgual(Integer menorIgual) {
        this.menorIgual = menorIgual;
    }
}
