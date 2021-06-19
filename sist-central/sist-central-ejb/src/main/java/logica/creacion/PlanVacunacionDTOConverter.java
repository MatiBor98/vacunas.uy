package logica.creacion;

import datos.dtos.PlanVacunacionDTO;
import datos.entidades.Enfermedad;
import datos.entidades.PlanVacunacion;
import datos.repositorios.EnfermedadRepositoryLocal;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlanVacunacionDTOConverter implements Converter<PlanVacunacionDTO, PlanVacunacion> {

    @EJB
    private EnfermedadRepositoryLocal enfermedadRepository;

    @Override
    public PlanVacunacion convert(PlanVacunacionDTO source) {
        PlanVacunacionBuilder builder = new PlanVacunacionBuilder();
        Enfermedad enfermedad = enfermedadRepository.find(source.getEnfermedad())
                .orElseThrow(this::noSeEncontroEnfermedadRuntimeException);
        return builder
                .setEnfermedad(enfermedad)
                .setInicio(source.getInicio())
                .setFin(source.getFin())
                .setNombre(source.getNombre())
                .createPlanVacunacion();
    }

    private RuntimeException noSeEncontroEnfermedadRuntimeException() {
        return new RuntimeException("No se encontro la enfermedad.");
    }
}
