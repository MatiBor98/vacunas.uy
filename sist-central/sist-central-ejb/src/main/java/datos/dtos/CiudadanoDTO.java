package datos.dtos;

import java.io.Serializable;

public class CiudadanoDTO implements Serializable{
    private long ci;
    private String nombre;
    private String email;

    public CiudadanoDTO() {}

    public CiudadanoDTO(long ci, String nombre, String email) {
        this.ci = ci;
        this.nombre = nombre;
        this.email = email;
    }

    public long getCi() {
        return ci;
    }

    public void setCi(long ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.valueOf(ci) + " " + nombre + " " + email;
    }


}

