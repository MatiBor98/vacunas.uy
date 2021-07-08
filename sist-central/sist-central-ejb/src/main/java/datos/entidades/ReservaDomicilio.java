package datos.entidades;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class ReservaDomicilio implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="reservaDomId")
    @SequenceGenerator(name="reservaDomId",sequenceName="reservaDomId", allocationSize=1)
	private int codigo;

	private Estado estadoAprobacion;
	
	private Estado estadoVacunacion;
	
	@ManyToOne
	@JoinColumn(name="ciCiudadano", nullable=false)
	private Ciudadano ciudadano;

	private Integer paraDosis;

	private Departamento departamento;
	
	private String localidad;
	
	private String direccion;

	private Etapa etapa;
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Estado getEstadoAprobacion() {
		return estadoAprobacion;
	}

	public void setEstadoAprobacion(Estado estado) {
		this.estadoAprobacion = estado;
	}
	
	public Estado getEstadoVacunacion() {
		return estadoVacunacion;
	}

	public void setEstadoVacunacion(Estado estado) {
		this.estadoVacunacion = estado;
	}
	
	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	public Integer getParaDosis() {
		return paraDosis;
	}

	public void setParaDosis(Integer paraDosis) {
		this.paraDosis = paraDosis;
	}
	
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
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
	
	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public ReservaDomicilio(Estado estadoAprobacion, Estado estadoVacunacion,Ciudadano ciudadano, Intervalo intervalo, int paraDosis,
			Departamento departamento, String localidad, String direccion, Etapa etapa) {
		super();
		this.estadoAprobacion = estadoAprobacion;
		this.estadoVacunacion = estadoVacunacion;
		this.ciudadano = ciudadano;
		this.paraDosis = paraDosis;
		this.departamento = departamento;
		this.direccion = direccion;
		this.localidad = localidad;
		this.etapa = etapa;
	}

	public ReservaDomicilio() {
		super();
	}

}

