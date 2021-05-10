package logica.creacion;

import datos.dtos.CiudadanoDTO;
import datos.entidades.Ciudadano;

public class CiudadanoDTOBuilder {
    private long ci;
    private String nombre;
    private String email;

    public CiudadanoDTOBuilder setCi(long ci) {
        this.ci = ci;
        return this;
    }

    public CiudadanoDTOBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public CiudadanoDTOBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public CiudadanoDTO createCiudadanoDTO() {
        validar();
        return new CiudadanoDTO(ci, nombre, email);
    }

    private void validar() {
        //TODO: Agregar Validaciones
    }

}
