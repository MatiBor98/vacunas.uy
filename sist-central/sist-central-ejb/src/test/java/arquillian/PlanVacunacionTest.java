package arquillian;

import datos.dtos.EtapaDTO;
import datos.dtos.PlanVacunacionDTO;
import logica.creacion.EtapaDTOBuilder;
import logica.creacion.PlanVacunacionDTOBuilder;
import logica.servicios.local.EtapaController;
import logica.servicios.local.PlanVacunacionServiceLocal;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class PlanVacunacionTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(JavaArchive.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/load.sql")
                .addPackages(true, "datos", "logica")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private EtapaController etapaController;

    @EJB
    private PlanVacunacionServiceLocal planVacunacionServiceLocal;

    @Test
    public void should_create_a_plan_and_etapa_for_it() {
        PlanVacunacionDTOBuilder planVacunacionBuilder = new PlanVacunacionDTOBuilder();
        EtapaDTOBuilder etapaBuilder = new EtapaDTOBuilder();

        PlanVacunacionDTO planVacunacion = planVacunacionBuilder
                .setEnfermedad("Corona Virus")
                .setNombre("Este es un plan de prueba")
                .setInicio(LocalDate.parse("2021-05-14"))
                .setFin(LocalDate.parse("2021-05-14").plusYears(1))
                .createPlanVacunacionDTO();
        planVacunacionServiceLocal.save(planVacunacion);

        EtapaDTO etapa = etapaBuilder.setDescripcion("Esto es una Etapa de prueba")
                .setInicio(LocalDate.parse("2021-05-14"))
                .setFin(LocalDate.parse("2021-05-14").plusYears(1))
                .setPlanVacunacion(planVacunacion.getNombre())
                .setVacuna("Coronavac")
                .createEtapaDTO();
        EtapaDTO etapaConId = etapaController.save(etapa);

        Optional<EtapaDTO> etapaDTO = etapaController.find(etapaConId.getId());

        assertNotNull(etapaDTO);
        assertTrue(etapaDTO.isPresent());
        assertEquals(etapaConId,etapaDTO.get());
    }
}
