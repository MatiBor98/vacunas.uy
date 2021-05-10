package logica.creacion;

import datos.entidades.Ciudadano;

public class CiudadanoBuilder {
    private long ci;
    private String nombre;
    private String email;

    public CiudadanoBuilder setCi(long ci) {
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

    public Ciudadano createCiudadano() {
        validar();
        return new Ciudadano(ci, nombre, email);
    }

    private void validar() {
        //TODO: Agregar Validaciones
    }

}
