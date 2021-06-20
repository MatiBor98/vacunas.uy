package datos.dtos;


import java.io.Serializable;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IntervaloDTO2 implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fechayHora;
    private AgendaDTO2 agenda2;

    public IntervaloDTO2(String fechayHora, AgendaDTO2 agenda) {
        this.fechayHora = fechayHora;
        this.agenda2 = agenda;
    }

    public IntervaloDTO2() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(String fechayHora) {
        this.fechayHora = fechayHora;
    }

    public AgendaDTO2 getAgenda2() {
        return agenda2;
    }
    public void setAgenda2(AgendaDTO2 agenda2) {
        this.agenda2 = agenda2;
    }

}