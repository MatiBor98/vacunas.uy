package datos.entidades;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class DosisVacunatorio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="dosisId")
	@SequenceGenerator(name="dosisId",sequenceName="dosisId", allocationSize=1)
	private int id;
	private int dosisDadas;
	
	@OneToOne
	private Vacuna vacuna;

	public int getDosisDadas() {
		return dosisDadas;
	}

	public void setDosisDadas(int dosisDadas) {
		this.dosisDadas = dosisDadas;
	}

	public Vacuna getVacuna() {
		return vacuna;
	}

	public void setVacuna(Vacuna vacuna) {
		this.vacuna = vacuna;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DosisVacunatorio() {
		super();
		this.dosisDadas = 0;
	}
	
	
	
}
