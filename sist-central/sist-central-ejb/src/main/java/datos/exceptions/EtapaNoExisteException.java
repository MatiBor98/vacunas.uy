package datos.exceptions;

public class EtapaNoExisteException extends IllegalArgumentException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



    public EtapaNoExisteException() {
    	super();
    }
    
    public EtapaNoExisteException(String s) {
    	super(s);
    }

    @Override
    public String getMessage() {

    	return "No existe un etapa registrada con el codigo ingresado";
    }
}
