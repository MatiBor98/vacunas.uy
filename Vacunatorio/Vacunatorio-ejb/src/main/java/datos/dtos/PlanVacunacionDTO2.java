package datos.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlanVacunacionDTO2 implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
    private String inicio; 
    private String fin;
    private String enfermedad;

    public PlanVacunacionDTO2(String nombre, String inicio2, String fin2, String enfermedad) {
        this.nombre = nombre;
        this.inicio = inicio2;
        this.fin = fin2;
        this.enfermedad = enfermedad;
    }
    
    public PlanVacunacionDTO2() {
    }
    

    public String getNombre() {
        return nombre;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
        return fin;
    }

    public String getEnfermedad() {
        return enfermedad;
    }
}
