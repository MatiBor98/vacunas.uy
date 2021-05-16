package datos.exceptions;

import java.util.NoSuchElementException;

public class VacunatorioNoExistenteException extends NoSuchElementException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "No existe un vacunatorio con el nombre especificado.";
	}

	public VacunatorioNoExistenteException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VacunatorioNoExistenteException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	
}
