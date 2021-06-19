package datos.exceptions;

public class EmailRegistradoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "No existe un usuario registrado con el email ingresado.";
	}

	public EmailRegistradoException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailRegistradoException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}
}
