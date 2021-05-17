package datos.exceptions;

public class AgendaFechaIncorrectaException extends IllegalArgumentException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final boolean fechaSolapan;
    private final boolean iniciaAntesQueEtapa;
    private final boolean terminaDespuesQueEtapa;


    public AgendaFechaIncorrectaException(boolean fechaSolapan, boolean iniciaAntesQueEtapa, boolean terminaDespuesQueEtapa) {
        this.fechaSolapan = fechaSolapan;
        this.iniciaAntesQueEtapa = iniciaAntesQueEtapa;
        this.terminaDespuesQueEtapa = terminaDespuesQueEtapa;
    }

    @Override
    public String getMessage() {

    	String separator = System.getProperty("line.separator");
    	
        StringBuilder msg = new StringBuilder("Error en las fecha: ");
        if(fechaSolapan)
            msg.append(separator);
            msg.append("La fecha de inicio no puede ser mayor a la fecha de fin.");
        if(iniciaAntesQueEtapa)
            msg.append(separator);
            msg.append("La fecha de inicio no puede ser menor a la de la etapa.");
        if(terminaDespuesQueEtapa)
            msg.append(separator);
            msg.append("La fecha de fin no puede ser mayor a la de la etapa.");
        return msg.toString();
    }
}
