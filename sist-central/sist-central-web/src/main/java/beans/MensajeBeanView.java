package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.google.gson.Gson;

import datos.dtos.MensajeDTO;

@Named("MensajeBeanView")
@SessionScoped
public class MensajeBeanView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<MensajeDTO> mensajes;
	
    private String text;

    private int ciUsuario;
    
    private static final ArrayList<String> colores = new ArrayList<String>(Arrays.asList(
    		"#db5ec2", "#84d6ff", "#c291db", "#e69f73", "#ff9bee", "#35cd96", "#91ab01", "#b87b76"
    		));
    
    
    public MensajeBeanView() {
		super();
		this.mensajes = new ArrayList<MensajeDTO>();
		try {
			ciUsuario = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ci"));			
		} catch (Exception e) {
			ciUsuario = 12345671;
		}
	}

	public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void send() {
    	//TODO Cambiar privilegios y también hacer que no estén hardcodeados los datos de usuario
    	String textoEscapeado = text.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n");

    	String textJson = new Gson().toJson(textoEscapeado);
    	
    	String script = String.format("sendMensaje('%s',%d,'%s', new Date().toISOString());", textJson, 50550419, "Nicolás San Martín");
	    PrimeFaces.current().executeScript(script);
	    clear();
    }
    public void clear() {
    	setText(null);
    }

	public List<MensajeDTO> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<MensajeDTO> mensajes) {
		this.mensajes = mensajes;
	}
	
	public void borrarMensajes() {
		this.mensajes = new ArrayList<MensajeDTO>();
	}
	
	
	public void addMensajeDTO() {
        String mensajeJSON = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param1");
		Gson gson = new Gson();
		MensajeDTO mensaje = gson.fromJson(mensajeJSON, MensajeDTO.class);
		this.mensajes.add(mensaje);
	}

	public int getCiUsuario() {
		return ciUsuario;
	}

	public void setCiUsuario(int ciUsuario) {
		this.ciUsuario = ciUsuario;
	}
	public String colorUsuario(int vacunadorCi) {
		ciUsuario = 12345671;
		try {
			ciUsuario = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ci"));			
		} catch (Exception e) {
		}
		String color = vacunadorCi == ciUsuario ? "" : "color:" + colores.get(vacunadorCi % 8);
		return color;
	}
}

