package datos.dtos;

import java.io.Serializable;

public class DosisVacunatorioDTO implements Serializable {


	private static final long serialVersionUID = 1L;
	private int dosisDadas;
	private VacunaDTO vacuna;

	public int getDosisDadas() {
		return dosisDadas;
	}

	public void setDosisDadas(int dosisDadas) {
		this.dosisDadas = dosisDadas;
	}

	public VacunaDTO getVacuna() {
		return vacuna;
	}

	public void setVacuna(VacunaDTO vacuna) {
		this.vacuna = vacuna;
	}

	public DosisVacunatorioDTO() {
		super();
		this.dosisDadas = 0;
	}
	
	
	
}
