package datos.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

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
