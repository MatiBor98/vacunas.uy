package logica.negocios;

import datos.entidades.Enfermedad;
import datos.repositorios.EnfermedadRepository;
import logica.servicios.local.EnfermedadController;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class EnfermedadBean implements EnfermedadController {
    @EJB
    private EnfermedadRepository enfermedadRepository;

    public EnfermedadBean() {}

    public List<Enfermedad> findPage(int primerResultado, int limiteResultados) {
        return enfermedadRepository.find(primerResultado, limiteResultados);
    }
}
