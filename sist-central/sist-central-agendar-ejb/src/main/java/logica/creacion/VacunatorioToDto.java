package logica.creacion;

import datos.dtos.VacunatorioDTO;
import datos.entidades.Vacunatorio;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VacunatorioToDto implements Converter<Vacunatorio, VacunatorioDTO> {
    @Override
    public VacunatorioDTO convert(Vacunatorio source) {
        return new VacunatorioDTO(source.getNombre(), source.getCiudad(), source.getDireccion(), source.getDepartamento());
    }
}
