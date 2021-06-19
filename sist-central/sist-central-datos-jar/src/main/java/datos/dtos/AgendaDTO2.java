package datos.dtos;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class AgendaDTO2 implements Serializable {
	private static final long serialVersionUID = 1L;

    private String nombre;
    private String inicio;
    private String fin;
    private EtapaDTO2 etapa;
    private TurnoDTO turno;

    public AgendaDTO2() {}

    public AgendaDTO2(String nombre, String inicio, String fin, EtapaDTO2 etapa, TurnoDTO turno) {
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
        this.etapa = etapa;
        this.turno = turno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public EtapaDTO2 getEtapa() {
        return etapa;
    }

    public void setEtapa(EtapaDTO2 etapa) {
        this.etapa = etapa;
    }
    
    public TurnoDTO getTurno() {
        return turno;
    }

    public void setTurno(TurnoDTO turno) {
        this.turno = turno;
    }

}
