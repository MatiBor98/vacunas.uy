package arquillian;

import datos.dtos.EtapaDTO;
import datos.dtos.PlanVacunacionDTO;
import logica.creacion.EtapaDTOBuilder;
import logica.creacion.PlanVacunacionDTOBuilder;
import logica.negocios.CiudadanoBean;
import logica.negocios.MensajeBean;
import logica.servicios.local.EtapaController;
import logica.servicios.local.PlanVacunacionServiceLocal;
import plataformainteroperabilidad.Trabajo;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class PlanVacunacionTest {

    @Deployment
    public static Archive<?> createDeployment() {
    	File[] files = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .resolve()
                .withTransitivity()
                .asFile();
    	
        return ShrinkWrap.create(WebArchive.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/load.sql")
            	.addPackages(true, "datos", "logica", "plataformainteroperabilidad")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsLibraries(files);
                
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
        assertTrue(etapaController.existeEtapaParaCiudadano("Corona Virus", 23, Trabajo.PUBLICO));
        
        List<Trabajo> trabajos = new ArrayList<Trabajo>();
        trabajos.add(Trabajo.PUBLICO);
        etapaController.save("Coronavac", LocalDate.of(2022, 5, 15), LocalDate.of(2022, 5, 15), planVacunacion.getNombre(), "pureba nro 2", trabajos, 18, 30);
        
        assertFalse(planVacunacionServiceLocal.find("Este es un plan de prueba").isEmpty());
        assertEquals(2, planVacunacionServiceLocal.find("Este es un plan de prueba").get().getEtapas().size());
        
        assertEquals(2, planVacunacionServiceLocal.find().size());

    }
}
