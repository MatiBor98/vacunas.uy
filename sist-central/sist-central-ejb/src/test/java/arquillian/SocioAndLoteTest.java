package arquillian;

import datos.dtos.LoteDTO;
import datos.entidades.Departamento;
import datos.entidades.Enfermedad;
import datos.entidades.Laboratorio;
import datos.entidades.Lote;
import datos.entidades.PlanVacunacion;
import datos.entidades.SocioLogistico;
import datos.entidades.Vacuna;
import datos.entidades.Vacunatorio;
import logica.schedule.DatosVacunatorio;
import logica.servicios.local.EnfermedadServiceLocal;
import logica.servicios.local.LaboratorioServiceLocal;
import logica.servicios.local.LoteServiceLocal;
import logica.servicios.local.PlanVacunacionServiceLocal;
import logica.servicios.local.SocioLogisticoControllerLocal;
import logica.servicios.local.VacunaServiceLocal;
import logica.servicios.local.VacunatorioControllerLocal;

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
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class SocioAndLoteTest {

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
    
    @EJB
    private SocioLogisticoControllerLocal socioLogisticoService;
    
    @EJB
    private LoteServiceLocal loteService;
    
    @EJB
    private VacunatorioControllerLocal vacunatorioController;

    @Test
    @InSequence(1)
    public void should_create_lote() {
    	    	
       enfermedadService.save("COVID-19", "alto bajon", new ArrayList<Vacuna>(), new ArrayList<PlanVacunacion>());
       List<Enfermedad> enf = enfermedadService.findByNombreEnfermedad("COVID-19");
       assertEquals(1, enf.size());

       laboratorioService.save("TrumpLabs", new ArrayList<Vacuna>());
       List<Laboratorio> lab = laboratorioService.findByNombreLaboratorio("TrumpLabs");
       assertEquals(1, lab.size());       
       
       vacunaService.save("TheWall", 3, 24, 15, lab, enf);
       List<Vacuna> vac = vacunaService.findByNombreVacuna("TheWall");
       assertEquals(1, vac.size());
       
       vacunatorioController.addVacunatorio("CASMU", "Bella Union", "Pepe 644", Departamento.Artigas);
       List<Vacunatorio> vacs = vacunatorioController.find();
       //se crean 3 al iniciar el sistema
       assertEquals(4, vacs.size());
       
       socioLogisticoService.addSocioLogistico("DHL");
       Optional<SocioLogistico> socio =  socioLogisticoService.find("DHL");
       assertFalse(socio.isEmpty());
       SocioLogistico socioLogistico = socio.get();
       assertEquals("DHL", socioLogistico.getNombre());
       assertFalse(socioLogistico.isHabilitado());
       
       socioLogisticoService.habilitarSocioLogistico("DHL");
       
       List<SocioLogistico> socios = socioLogisticoService.findHabilitados();
       assertEquals(1,socios.size());
       socioLogistico = socios.get(0);
       assertEquals("DHL", socioLogistico.getNombre());
       assertTrue(socioLogistico.isHabilitado());
       
       loteService.addLote(400, 20, "CASMU", LocalDate.of(2022, 7, 22), "TheWall", "DHL");
       Optional<Lote> lote = loteService.findById(20);
       assertFalse(lote.isEmpty());
       Lote loteVacuna = lote.get();
       assertEquals(20, loteVacuna.getNumeroLote().intValue());
       assertEquals(400, loteVacuna.getDosisDisponibles());
       assertEquals("TheWall",loteVacuna.getVacuna().getNombre());
       assertEquals("DHL",loteVacuna.getSocioLogistico().getNombre());
       assertTrue(LocalDate.of(2022, 7, 22).equals(loteVacuna.getFechaVencimiento()));
       assertNull(loteVacuna.getFechaDespacho());
       assertNull(loteVacuna.getFechaEntrega());
       
       loteService.addLote(450, 21, "CASMU", LocalDate.of(2022, 7, 22), "TheWall", "DHL");
       
       loteService.despacharLote(20, "DHL", LocalDate.of(2021, 8, 15));
       lote = loteService.findById(20);
       loteVacuna = lote.get();
       assertNotNull(loteVacuna.getFechaDespacho());
       assertTrue(LocalDate.of(2021, 8, 15).equals(loteVacuna.getFechaDespacho()));
       
       loteService.entregarLote(20, "DHL", LocalDate.of(2021, 8, 16));
       lote = loteService.findById(20);
       loteVacuna = lote.get();
       assertNotNull(loteVacuna.getFechaEntrega());
       assertTrue(LocalDate.of(2021, 8, 16).equals(loteVacuna.getFechaEntrega()));

       List<Lote> lotes = loteService.find();
       //hay 2 creados al iniciar el sistema
       assertEquals(4, lotes.size());
       
       DatosVacunatorio datosVac = vacunatorioController.getDatosVacunatorio("CASMU");
       assertEquals("CASMU", datosVac.getVac().getNombre());
       assertEquals(2, datosVac.getVac().getLotes().size());
    }
    
}
