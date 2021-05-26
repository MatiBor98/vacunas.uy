package logica.creacion;

import datos.dtos.CiudadanoDTO;

public class CiudadanoDTOBuilder {
    private int ci;
    private String nombre;
    private String email;
    private boolean vacunador;

    public CiudadanoDTOBuilder setCi(int ci) {
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
        return new CiudadanoDTO(ci, nombre, email, vacunador);
    }



	public CiudadanoDTOBuilder setVacunador(boolean vacunador) {
		this.vacunador = vacunador;
		return this;
	}

	private void validar() {
        //TODO: Agregar Validaciones
    }

}
