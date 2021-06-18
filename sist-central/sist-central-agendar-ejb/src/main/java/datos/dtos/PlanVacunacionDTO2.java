package datos.dtos;

import java.io.Serializable;

public class PlanVacunacionDTO2 implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String nombre;
    private final String inicio;
    private final String fin;
    private final String enfermedad;

    public PlanVacunacionDTO2(String nombre, String inicio2, String fin2, String enfermedad) {
        this.nombre = nombre;
        this.inicio = inicio2;
        this.fin = fin2;
        this.enfermedad = enfermedad;
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
