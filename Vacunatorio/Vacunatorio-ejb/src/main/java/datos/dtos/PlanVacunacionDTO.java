package datos.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlanVacunacionDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String nombre;
    private final LocalDate inicio; 
    private final LocalDate fin;
    private final String enfermedad;

    public PlanVacunacionDTO(String nombre, LocalDate inicio2, LocalDate fin2, String enfermedad) {
        this.nombre = nombre;
        this.inicio = inicio2;
        this.fin = fin2;
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
