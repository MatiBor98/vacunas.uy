package datos.entidades;

import javax.persistence.*;

@Entity
public class Reserva {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="reservaId")
    @SequenceGenerator(name="reservaId",sequenceName="reservaId", allocationSize=1)
	private long codigo;
	
	private Estado estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ciCiudadano", nullable=false)
	private Ciudadano ciudadano;

	@ManyToOne
	@JoinColumn(name="intervaloId", nullable=false)
	private Intervalo intervalo;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
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

	public Reserva(Estado estado, Ciudadano ciudadano) {
		super();
		this.estado = estado;
		this.ciudadano = ciudadano;
	}

	public Reserva() {
		super();
	}
	
	
}
