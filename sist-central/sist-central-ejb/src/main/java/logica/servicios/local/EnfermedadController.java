package logica.servicios.local;

import datos.entidades.Enfermedad;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EnfermedadController {
    List<Enfermedad> findPage(int primerResultado, int limiteResultados);
}
