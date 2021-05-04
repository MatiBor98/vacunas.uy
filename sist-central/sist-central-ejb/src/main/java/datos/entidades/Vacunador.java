package datos.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import datos.entidades.Laboratorio;

@Entity
@DiscriminatorValue("Vacunador")
public class Vacunador extends Ciudadano implements Serializable{
	
	
}