package logica.creacion;

import datos.dtos.EtapaDTO;
import datos.entidades.Etapa;
import datos.entidades.PlanVacunacion;
import datos.entidades.Vacuna;
import datos.repositorios.PlanVacunacionRepository;
import datos.repositorios.VacunaRepository;
import datos.repositorios.VacunaRepositoryLocal;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.Optional;

@ApplicationScoped
public class EtapaDTOCoverter implements Converter<EtapaDTO, Etapa> {

    @EJB
    private PlanVacunacionRepository planVacunacionRepository;

    @EJB
    private VacunaRepositoryLocal vacunaRepository;

    @Override
    public Etapa convert(EtapaDTO source) {
        EtapaBuilder builder = new EtapaBuilder();
        PlanVacunacion planVacunacion = planVacunacionRepository.find(source.getPlanVacunacion())
                .orElseThrow(this::noSeEncontroPlanRuntimeException);

        Vacuna vacuna = vacunaRepository.find(source.getVacuna()).orElseThrow(this::noSeEncontroVacunaRuntimeException);

        return builder.setInicio(source.getInicio())
                .setFin(source.getFin())
                .setDescripcion(source.getDescripcion())
                .setRestricciones(null)
                .setPlanVacunacion(planVacunacion)
                .setVacuna(vacuna)
                .setId(source.getId())
                .createEtapa();
    }

    private RuntimeException noSeEncontroPlanRuntimeException() {
        return new RuntimeException("No se encontro plan.");
    }

    private RuntimeException noSeEncontroVacunaRuntimeException() {
        return new RuntimeException("No se encontro Vacuna.");
    }
}