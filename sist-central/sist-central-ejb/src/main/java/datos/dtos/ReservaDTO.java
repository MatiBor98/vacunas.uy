package datos.dtos;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import datos.entidades.Estado;
@XmlRootElement
public class ReservaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private int codigo;
	private Estado estado;
	private CiudadanoDTO ciudadano;
	private IntervaloDTO2 intervalo;
	private int paraDosis;
	private String lote;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public CiudadanoDTO getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(CiudadanoDTO ciudadano) {
		this.ciudadano = ciudadano;
	}

	public IntervaloDTO2 getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(IntervaloDTO2 intervalo) {
		this.intervalo = intervalo;
	}

	public int getParaDosis() {
		return paraDosis;
	}

	public void setParaDosis(Integer paraDosis) {
		this.paraDosis = paraDosis;
	}

	public ReservaDTO(Estado estado, CiudadanoDTO ciudadano, IntervaloDTO2 intervalo, int paraDosis, String lote) {
		super();
		this.estado = estado;
		this.ciudadano = ciudadano;
		this.intervalo = intervalo;
		this.paraDosis = paraDosis;
		this.lote = lote;
	}

	public ReservaDTO() {}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}


}

