package datos.entidades;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Reserva implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="reservaId")
	@SequenceGenerator(name="reservaId",sequenceName="reservaId", allocationSize=1)
	private int codigo;

	private Estado estado;
	private String lote;

	@ManyToOne
	@JoinColumn(name="ciCiudadano", nullable=false)
	private Ciudadano ciudadano;

	@ManyToOne
	@JoinColumn(name="intervaloId", nullable=false)
	private Intervalo intervalo;

	private Integer paraDosis;
	
	private boolean aDomicilio;
	
	private EstadoAprobacion estadoAprobacion;
	
	private String localidad;
	
	private String direccion;

	private String detalles;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
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

	public Integer getParaDosis() {
		return paraDosis;
	}

	public void setParaDosis(Integer paraDosis) {
		this.paraDosis = paraDosis;
	}

	public Reserva(Estado estado, Ciudadano ciudadano, Intervalo intervalo, int paraDosis) {
		super();
		this.estado = estado;
		this.ciudadano = ciudadano;
		this.intervalo = intervalo;
		this.paraDosis = paraDosis;
		this.lote = null;
		this.aDomicilio = false;
		this.detalles = null;
		this.direccion = null;
		this.estadoAprobacion = null;
		this.localidad = null;
	}
	
	public Reserva(Estado estado, Ciudadano ciudadano, Intervalo intervalo, int paraDosis, String detalles,
			String direccion, String localidad, EstadoAprobacion estadoAprobacion) {
		super();
		this.estado = estado;
		this.ciudadano = ciudadano;
		this.intervalo = intervalo;
		this.paraDosis = paraDosis;
		this.lote = null;
		this.aDomicilio = true;
		this.detalles = detalles;
		this.direccion = direccion;
		this.estadoAprobacion = estadoAprobacion;
		this.localidad = localidad;
	}
	
	public Reserva() {
		super();
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public EstadoAprobacion getEstadoAprobacion() {
		return estadoAprobacion;
	}

	public void setEstadoAprobacion(EstadoAprobacion estadoAprobacion) {
		this.estadoAprobacion = estadoAprobacion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public boolean isaDomicilio() {
		return aDomicilio;
	}

	public void setaDomicilio(boolean aDomicilio) {
		this.aDomicilio = aDomicilio;
	}


}

