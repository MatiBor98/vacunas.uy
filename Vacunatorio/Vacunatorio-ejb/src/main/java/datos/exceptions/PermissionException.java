package datos.exceptions;

public class PermissionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "No tiene permiso para realizar esta operacion.";
	}

	public PermissionException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PermissionException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}
}
