package datos.entidades;

import javax.persistence.Entity;

@Entity
public class PuestoVacunacion {
	private Vacunatorio vacunatorio;
	private Asignacion asignacion;
	
	private String nombrePuesto;
}
