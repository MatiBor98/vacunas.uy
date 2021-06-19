package datos.entidades;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class SocioLogistico implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String nombre;

	@OneToMany(mappedBy = "socioLogistico")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Lote> lotes;

	private boolean habilitado;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public SocioLogistico(String nombre, List<Lote> lotes, boolean habilitado) {
		super();
		this.nombre = nombre;
		this.lotes = lotes;
		this.habilitado = habilitado;
	}

	public SocioLogistico() {
		super();
	}


	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}
}
