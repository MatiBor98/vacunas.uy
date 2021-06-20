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
	private int codigo;
	
	private Estado estado;

	@ManyToOne
	@JoinColumn(name="ciCiudadano", nullable=false)
	private Ciudadano ciudadano;

	@ManyToOne
	@JoinColumn(name="intervaloId", nullable=false)
	private Intervalo intervalo;

	private Integer paraDosis;
	private String lote;

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

	public Reserva(Estado estado, Ciudadano ciudadano, Intervalo intervalo, int paraDosis, String lote) {
		super();
		this.estado = estado;
		this.ciudadano = ciudadano;
		this.intervalo = intervalo;
		this.paraDosis = paraDosis;
		this.lote = lote;
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
	
	
}
