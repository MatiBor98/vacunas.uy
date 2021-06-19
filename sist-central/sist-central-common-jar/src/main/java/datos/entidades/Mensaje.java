package datos.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
public class Mensaje implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaEtapaId")
    @SequenceGenerator(name="mensajeId",sequenceName="mensajeId", allocationSize=1)
    private int id;
	
	@ManyToOne
    @JoinColumn(name="vacunadorCI", nullable=false)
	private Vacunador vacunador;
	private LocalDateTime fechaHora;
	private String texto;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getMensaje() {
		return texto;
	}
	public void setMensaje(String mensaje) {
		this.texto = mensaje;
	}
	public Vacunador getVacunador() {
		return this.vacunador;
	}
	public void setVacunador(Vacunador vac) {
		this.vacunador = vac;
	}
	public Mensaje(int id, LocalDateTime fechaHora, String mensaje, Vacunador vac) {
		super();
		this.id = id;
		this.fechaHora = fechaHora;
		this.texto = mensaje;
		this.vacunador = vac;
	}
	public Mensaje() {
		super();
	}
	
	
	
}
