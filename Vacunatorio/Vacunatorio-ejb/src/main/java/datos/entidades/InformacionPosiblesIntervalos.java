package datos.entidades;

import java.time.LocalTime;

import javax.persistence.Embeddable;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.io.Serializable;

@Embeddable
public class InformacionPosiblesIntervalos implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalTime inicio;
    private LocalTime fin;
    private int capacidadPorTurno;
    private int minutosTurno;

    public InformacionPosiblesIntervalos(LocalTime inicio, LocalTime fin, int capacidadPorTurno, int minutosTurno) {
        this.inicio = inicio;
        this.fin = fin;
        this.capacidadPorTurno = capacidadPorTurno;
        this.minutosTurno = minutosTurno;
    }

    public InformacionPosiblesIntervalos() {

    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public int getCapacidadPorTurno() {
        return capacidadPorTurno;
    }

    public int getMinutosTurno() {
        return minutosTurno;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    public void setCapacidadPorTurno(int capacidadPorTurno) {
        this.capacidadPorTurno = capacidadPorTurno;
    }

    public void setMinutosTurno(int minutosTurno) {
        this.minutosTurno = minutosTurno;
    }

    public long calcularCantidadIntervalos() {
        return (MINUTES.between(inicio, fin) / minutosTurno)* capacidadPorTurno;
    }
}