package datos.entidades;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class ReservaConfirmada implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int codigo;	
	
	private String lote;
	
	private String estado;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public ReservaConfirmada(int codigo, String lote, String estado) {
		super();
		this.codigo = codigo;
		this.lote = lote;
		this.estado = estado;
	}

	public ReservaConfirmada() {
		super();
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
