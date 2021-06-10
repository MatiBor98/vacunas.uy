package arquillian;

import datos.entidades.Enfermedad;
import datos.entidades.Laboratorio;
import datos.entidades.PlanVacunacion;
import datos.entidades.Vacuna;

import logica.servicios.local.EnfermedadServiceLocal;
import logica.servicios.local.LaboratorioServiceLocal;
import logica.servicios.local.PlanVacunacionServiceLocal;
import logica.servicios.local.VacunaServiceLocal;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.ejb.EJB;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class EnfermedadTest {

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
    private EnfermedadServiceLocal enfermedadService;

    @EJB 
    private VacunaServiceLocal vacunaService;
    
    @EJB
    private PlanVacunacionServiceLocal planVacunacionService;
    
    @EJB
    private LaboratorioServiceLocal laboratorioService;
    
    @Test
    @InSequence(1)
    public void should_create_enfermedad() {
    	    	
        enfermedadService.save("COVID-19", "Te agarra una tos y alto bajon bro", new ArrayList<Vacuna>(), new ArrayList<PlanVacunacion>());
        
        List<Enfermedad> enfermedad = enfermedadService.findByNombreEnfermedad("COVID-19");
        
        assertEquals(1, enfermedad.size());
        
        Enfermedad enf = enfermedad.get(0);
        
        assertEquals("COVID-19", enf.getNombre());
        assertEquals("Te agarra una tos y alto bajon bro", enf.getDescripcion());
        assertEquals(0, enf.getVacunas().size());
        assertEquals(0, enf.getPlanesVacunacion().size());
        
        enfermedadService.save("Dengue", "Altas ganas de vomitar y no te mueras pls", new ArrayList<Vacuna>(), new ArrayList<PlanVacunacion>());
        
        //son 3 pq hay una enferemdada que se crea cuando inicia el sistema
        assertEquals(3, enfermedadService.find().size());
        planVacunacionService.save("COVID-19-PLAN", LocalDate.of(2021, 6, 20), LocalDate.of(2021, 12, 20), enf);
        
        laboratorioService.save("PutinLabs", new ArrayList<Vacuna>());
        List<Laboratorio> labs =  laboratorioService.findByNombreLaboratorio("PutinLabs");
        
        vacunaService.save("Sputnik-V", 2, 6, 30, labs, enfermedad);
        vacunaService.save("Sputnik-Vrvgvay", 1, 36, 30, labs, enfermedad);

        enfermedad = enfermedadService.findByNombreEnfermedad("COVID-19");
        enf = enfermedad.get(0);
        
        int vacunas = 0;
        for(Vacuna vacuna: enf.getVacunas()) {
        	if(vacuna.getNombre().equals("Sputnik-V"))
        		vacunas++;
        	else if(vacuna.getNombre().equals("Sputnik-Vrvgvay"))
        		vacunas++;
        	else
        		fail("tiene una vacuna que no deberia");
        }
        PlanVacunacion plan = enf.getPlanesVacunacion().get(0);
        
        
        assertEquals(2, vacunas);
        assertEquals(1, enf.getPlanesVacunacion().size());
        assertNotNull(plan);
        assertEquals("COVID-19-PLAN", plan.getNombre());
        
        
    }
    
   /* @Test
    @InSequence(2)
    public void should_delete_enfermedad() {
    	
    	
    	
        enfermedadService.eliminar("COVID-19");
        
        List<Enfermedad> enfermedad = enfermedadService.findByNombreEnfermedad("COVID-19");
        assertEquals(0, enfermedad.size());

    }*/
    

}
