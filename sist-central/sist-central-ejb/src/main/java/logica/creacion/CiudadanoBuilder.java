package logica.creacion;

import datos.entidades.Ciudadano;
import datos.entidades.Vacunador;

public class CiudadanoBuilder {
    private int ci;
    private String nombre;
    private String email;
    private boolean vacunador;

    public CiudadanoBuilder setCi(int ci) {
        this.ci = ci;
        return this;
    }

    public CiudadanoBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public CiudadanoBuilder setEmail(String email) {
        this.email = email;
        return this;
    }
    
    public CiudadanoBuilder setVacunador(boolean vacunador) {
    	this.vacunador = vacunador;
    	return this;
    }

    public Ciudadano createCiudadano() {
        validar();
        if(vacunador) {
            return new Vacunador(ci, nombre, email);
        }
        return new Ciudadano(ci, nombre, email);
    }

	private void validar() {
        //TODO: Agregar Validaciones
    }

}
