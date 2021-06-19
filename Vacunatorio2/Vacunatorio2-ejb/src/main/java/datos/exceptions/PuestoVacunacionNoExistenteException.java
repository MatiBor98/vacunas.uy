package datos.exceptions;

import java.util.NoSuchElementException;

public class PuestoVacunacionNoExistenteException extends NoSuchElementException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "No existe un puesto de vacunación con la id especificada.";
	}

	public PuestoVacunacionNoExistenteException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PuestoVacunacionNoExistenteException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	
}
