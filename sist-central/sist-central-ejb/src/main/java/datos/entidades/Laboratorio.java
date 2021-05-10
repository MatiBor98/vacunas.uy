package datos.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Map;

@Entity
public class Laboratorio implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="laboratorioId")
	@SequenceGenerator(name="laboratorioId",sequenceName="laboratorioId", allocationSize=1)
	private long id;

	private String nombre;

	public Laboratorio() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
