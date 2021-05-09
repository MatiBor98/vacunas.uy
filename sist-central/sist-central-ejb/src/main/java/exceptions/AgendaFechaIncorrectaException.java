package exceptions;

public class AgendaFechaIncorrectaException extends IllegalArgumentException {
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

        StringBuilder msg = new StringBuilder("Error en las fecha: ");
        if(fechaSolapan)
            msg.append(System.getProperty("line.separator"));
            msg.append("La fecha de inicio no puede ser mayor a la fecha de fin.");
        if(iniciaAntesQueEtapa)
            msg.append(System.getProperty("line.separator"));
            msg.append("La fecha de inicio no puede ser menor a la de la etapa.");
        if(terminaDespuesQueEtapa)
            msg.append(System.getProperty("line.separator"));
            msg.append("La fecha de fin no puede ser mayor a la de la etapa.");
        return msg.toString();
    }
}
