package datos.exceptions;

public class CiudadanoRegistradoException extends Exception {

	@Override
	public String getMessage() {
		return "Ya existe un usuario registrado con la ci ingresada.";
	}

	public CiudadanoRegistradoException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CiudadanoRegistradoException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}
}
