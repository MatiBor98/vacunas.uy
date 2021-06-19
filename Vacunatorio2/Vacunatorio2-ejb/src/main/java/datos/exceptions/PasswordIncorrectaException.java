package datos.exceptions;

public class PasswordIncorrectaException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "La contrasena ingresada no es correcta. Intentelo nuevamente.";
	}

	public PasswordIncorrectaException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PasswordIncorrectaException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}
}
