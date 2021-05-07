package datos.entidades;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Reserva {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="reservaId")
    @SequenceGenerator(name="reservaId",sequenceName="reservaId", allocationSize=1)
	private String codigo;
	
	private Estado estado;

	private Ciudadano ciudadano;
	private Intervalo intervalo;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	public Intervalo getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Intervalo intervalo) {
		this.intervalo = intervalo;
	}

	public Reserva(Estado estado, Ciudadano ciudadano, Intervalo intervalo) {
		super();
		this.estado = estado;
		this.ciudadano = ciudadano;
		this.intervalo = intervalo;
	}

	public Reserva() {
		super();
	}
	
	
}
