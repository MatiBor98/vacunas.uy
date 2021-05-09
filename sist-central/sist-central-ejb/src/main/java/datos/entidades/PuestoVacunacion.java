package datos.entidades;

import javax.persistence.*;

@Entity
public class PuestoVacunacion {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaAgendaId")
	@SequenceGenerator(name="puestoId",sequenceName="puestoId", allocationSize=1)
	private long id;

	@ManyToOne
	@JoinColumn(name="vacunatorioId", nullable=false)
	private Vacunatorio vacunatorio;

	private String nombrePuesto;

	public Vacunatorio getVacunatorio() {
		return vacunatorio;
	}

	public void setVacunatorio(Vacunatorio vacunatorio) {
		this.vacunatorio = vacunatorio;
	}

	public String getNombrePuesto() {
		return nombrePuesto;
	}

	public void setNombrePuesto(String nombrePuesto) {
		this.nombrePuesto = nombrePuesto;
	}

	public PuestoVacunacion(Vacunatorio vacunatorio, String nombrePuesto) {
		super();
		this.vacunatorio = vacunatorio;
		this.nombrePuesto = nombrePuesto;
	}

	public PuestoVacunacion() {
		super();
	}
	
	
}
