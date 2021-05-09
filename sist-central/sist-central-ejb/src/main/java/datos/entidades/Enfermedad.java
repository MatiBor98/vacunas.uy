package datos.entidades;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.*;

@Entity
public class Enfermedad implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaEnfermedadId")
	@SequenceGenerator(name="sequenciaEnfermedadId",sequenceName="seq_enfermedad_id", allocationSize=1)
	private long id;
	private String nombre;
	private String descripcion;
	
	public Enfermedad() {}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String desc) {
		this.descripcion = desc;
	}
}
