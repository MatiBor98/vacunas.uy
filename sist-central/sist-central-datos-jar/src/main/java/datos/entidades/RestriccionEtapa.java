package datos.entidades;

import plataformainteroperabilidad.Trabajo;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Embeddable
public class RestriccionEtapa {
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Trabajo> filtroEmpleoEn = Collections.emptyList();
    private Integer mayorIgual;
    private Integer menorIgual;

    public RestriccionEtapa(List<Trabajo> trabajos, int edadMin, int edadMax) {
    	this.filtroEmpleoEn = trabajos;
    	this.mayorIgual = edadMin;
    	this.menorIgual = edadMax;
    }
    
    public RestriccionEtapa() {
    }
    
    public List<Trabajo> getFiltroEmpleoEn() {
        return filtroEmpleoEn;
    }

    public void setFiltroEmpleoEn(List<Trabajo> filtroEmpleoEn) {
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
