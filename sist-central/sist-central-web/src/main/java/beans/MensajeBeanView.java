package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

@Named("MensajeBeanView")
@RequestScoped
public class MensajeBeanView {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void send() {
    	//TODO Cambiar privilegios y también hacer que no estén hardcodeados los datos de usuario
    	String script = String.format("sendMensaje('%s',%d,'%s', new Date().toISOString());", text, 50550419, "Nicolás San Martín");
	    PrimeFaces.current().executeScript(script);
    }
}

