package datos.exceptions;

public class EmailNoRegistradoException extends Exception {

	@Override
	public String getMessage() {
		return "No existe un usuario registrado con el email ingresado.";
	}

	public EmailNoRegistradoException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailNoRegistradoException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}
}
