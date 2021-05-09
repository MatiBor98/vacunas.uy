package datos.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class Mensaje implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequenciaEtapaId")
    @SequenceGenerator(name="mensajeId",sequenceName="mensajeId", allocationSize=1)
    private long id;
	
	private LocalDateTime fechaHora;
	private String mensaje;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Mensaje(long id, LocalDateTime fechaHora, String mensaje) {
		super();
		this.id = id;
		this.fechaHora = fechaHora;
		this.mensaje = mensaje;
	}
	public Mensaje() {
		super();
	}
	
	
	
}
