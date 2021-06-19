package datos.dtos;

public class MensajesChangedEvent {
	private MensajeDTO listaMensajes;

    public MensajesChangedEvent(MensajeDTO listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    public MensajeDTO getMensaje() {
        return listaMensajes;
    }


}
