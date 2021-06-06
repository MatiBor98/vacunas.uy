package laboratorio.tse;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("LoteBean")
@ViewScoped
public class LoteBean implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private int numeroLote;
	private String vacuna;
	private int dosisDisp;
	private String vacunatorio;
	private String fechaVencimiento;
	private String fechaEntrega;
	private String fechaDespacho;
	
	public int getNumeroLote() {
		return numeroLote;
	}
	public void setNumeroLote(int numeroLote) {
		this.numeroLote = numeroLote;
	}
}
