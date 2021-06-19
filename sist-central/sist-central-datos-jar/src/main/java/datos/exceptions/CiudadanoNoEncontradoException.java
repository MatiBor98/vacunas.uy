package datos.exceptions;

public class CiudadanoNoEncontradoException extends Exception {

	@Override
	public String getMessage() {
		return "No existe un usuario registrado con la ci ingresado.";
	}

	public CiudadanoNoEncontradoException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CiudadanoNoEncontradoException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}
}
