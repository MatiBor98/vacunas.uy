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
public class LaboratorioTest {

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
    private LaboratorioServiceLocal laboratorioService;
    
    @Test
    @InSequence(1)
    public void should_create_laboratorio() {
    	    	
       enfermedadService.save("COVID-19", "alto bajon", new ArrayList<Vacuna>(), new ArrayList<PlanVacunacion>());
       List<Enfermedad> enf = enfermedadService.findByNombreEnfermedad("COVID-19");
       assertEquals(1, enf.size());

       laboratorioService.save("TrumpLabs", new ArrayList<Vacuna>());
       List<Laboratorio> lab = laboratorioService.findByNombreLaboratorio("TrumpLabs");
       assertEquals(1, lab.size());
       
       laboratorioService.save("BidenLabs", new ArrayList<Vacuna>());
       
       
       vacunaService.save("TheWall", 3, 24, 15, lab, enf);
       List<Vacuna> vac = vacunaService.findByNombreVacuna("TheWall");
       assertEquals(1, vac.size());
       
       lab = laboratorioService.find();
       assertEquals(2, lab.size());
       Laboratorio laboratorio = null;
       if(lab.get(0).getNombre().equals("TrumpLabs")) {
    	   laboratorio = lab.get(0);
       }
       else {
    	   laboratorio = lab.get(1);
       }
       
       assertNotNull(laboratorio);
       assertEquals("TrumpLabs", laboratorio.getNombre());
       assertEquals("TheWall", laboratorio.getVacunas().get(0).getNombre());
    }
    
    @Test
    @InSequence(1)
    public void should_delete_laboratorio() {
    	
    	vacunaService.eliminar("TheWall");
    	List<Vacuna> vac = vacunaService.findByNombreVacuna("TheWall");
		assertEquals(0, vac.size());

    	laboratorioService.eliminar("TrumpLabs");
    	List<Laboratorio> lab = laboratorioService.findByNombreLaboratorio("TrumpLabs");
    	assertEquals(0, lab.size());
   		
		List<Enfermedad> enf = enfermedadService.findByNombreEnfermedad("COVID-19");
		//la enfermedad no deberia eliminarse
    	assertEquals(1, enf.size());
    	
		lab = laboratorioService.find();
		assertEquals(1, lab.size());

	}
    
}
