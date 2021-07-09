package arquillian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import datos.dtos.PuestoVacunacionDTO;
import datos.entidades.Asignacion;
import datos.entidades.Departamento;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Turno;
import datos.entidades.Vacunador;
import datos.entidades.Vacunatorio;
import datos.exceptions.CiudadanoRegistradoException;
import datos.repositorios.VacunatorioRepositoryLocal;
import logica.creacion.CiudadanoDTOBuilder;
import logica.negocios.CiudadanoBean;
import logica.negocios.MensajeBean;
import logica.negocios.VacunatorioBean;
import logica.schedule.DatosVacunatorio;
import logica.servicios.local.CiudadanoServiceLocal;
import logica.servicios.local.PuestoVacunacionBeanLocal;
import logica.servicios.local.TurnoServiceLocal;
import logica.servicios.local.VacunatorioControllerLocal;

@RunWith(Arquillian.class)
public class VacunatorioControllerTest {

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
    VacunatorioControllerLocal vacunatorioControllerLocal;
   
    @EJB
    TurnoServiceLocal turnoService;
    
    String nombreVacPrueba = "VacunatorioPruebaXYZ0123";

    @Test
    @InSequence(1)
    public void should_create_vacunatorio() {
    	//primero vamos a ver si se cargaron los del script de carga
    	
    	List<Vacunatorio> vacs = vacunatorioControllerLocal.find();
    	assertEquals(3, vacs.size());
    	
    	vacs = vacunatorioControllerLocal.findByDepartamento(Departamento.Artigas);
    	assertEquals(1, vacs.size());
    	
    	//ahora si creamos uno
    	List<String> deps = vacunatorioControllerLocal.getNombresDepartamentos();
    	assertEquals(19, deps.size());
    	
    	//GeometryFactory geomFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
    	
    	String password = UUID.randomUUID().toString();
		vacunatorioControllerLocal.addVacunatorio(nombreVacPrueba, "Mdeo", "Calle Facultad 3027", Departamento.Artigas, password);
		
        Vacunatorio vac = vacunatorioControllerLocal.findWithEverything(nombreVacPrueba).get();
        assertEquals(vac.getCiudad(), "Mdeo");
        assertEquals(vac.getDepartamento(), Departamento.Artigas);
        assertEquals(vac.getDireccion(), "Calle Facultad 3027");
        assertEquals(vac.getPuestosVacunacion().isEmpty(), true);
        
        DatosVacunatorio datosVac = vacunatorioControllerLocal.getDatosVacunatorio(nombreVacPrueba);
        assertEquals(nombreVacPrueba, datosVac.getVac().getNombre());
        assertEquals(datosVac.getVac().getCiudad(), "Mdeo");
        assertEquals(datosVac.getVac().getDepartamento(), Departamento.Artigas);
        assertEquals(datosVac.getVac().getDireccion(), "Calle Facultad 3027");
        assertEquals(datosVac.getVac().getPuestosVacunacion().isEmpty(), true);
    }
    
    @EJB
    PuestoVacunacionBeanLocal puestoVacunacionBeanLocal;
    
    @Test
    @InSequence(2)
    public void should_add_puestoVacunacion() {
    	String nombrePuestoPrueba = "Puesto 1";
    	puestoVacunacionBeanLocal.addPuestoVacunacion(nombrePuestoPrueba, nombreVacPrueba);
        Vacunatorio vac = vacunatorioControllerLocal.findWithEverything(nombreVacPrueba).get();
        assertEquals(nombrePuestoPrueba, vac.getPuestosVacunacion().get(0).getNombrePuesto());
        
    	puestoVacunacionBeanLocal.addPuestoVacunacion("Puesto 2", nombreVacPrueba);
        Vacunatorio vacMasTarde = vacunatorioControllerLocal.findWithEverything(nombreVacPrueba).get();
        assertEquals(2, vacMasTarde.getPuestosVacunacion().size());
    }

    @Test
    @InSequence(3)
    public void should_add_Turno() {
    	turnoService.addTurno("Matutino", nombreVacPrueba,LocalTime.of(8, 0), LocalTime.of(14, 0));
    	turnoService.addTurno("Vespertino", nombreVacPrueba,LocalTime.of(16, 0), LocalTime.of(20, 0));
    	
        Vacunatorio vac = vacunatorioControllerLocal.findWithEverything(nombreVacPrueba).get();
        assertEquals(2, vac.getPuestosVacunacion().size());

    }
    
    @EJB
    CiudadanoServiceLocal ciudadanoService;
    
    
    @Test
    @InSequence(4)
    public void should_add_Asignacion() {
    	
    	CiudadanoDTOBuilder builder = new CiudadanoDTOBuilder();
        
        builder.setCi(98765432);
        builder.setEmail("vacunador@email.com");
        builder.setNombre("Vac Un Ador");
        builder.setVacunador(true);
        
        try {
			ciudadanoService.save(builder.createCiudadanoDTO());
		} catch (CiudadanoRegistradoException e) {
			fail("vacunador no deberia estar registrado ya");
		}
        
        Optional<Vacunatorio> vac = vacunatorioControllerLocal.find(nombreVacPrueba);
        assertFalse(vac.isEmpty());
        List<Turno> turnos = vac.get().getTurnos();
        assertEquals(2, turnos.size());
        Turno turno = null;
        if(turnos.get(0).getNombre().equals("Matutino")) {
        	turno = turnos.get(0);
        }
        else {
        	turno = turnos.get(1);
        }
        
        List<PuestoVacunacion> puestos = vac.get().getPuestosVacunacion();
        assertEquals(2, puestos.size());
        PuestoVacunacion puesto = null;
        if(puestos.get(0).getNombrePuesto().equals("Puesto 2")) {
        	puesto = puestos.get(0);
        }
        else {
        	puesto = puestos.get(1);
        }
        List<Vacunador> vacs =  puestoVacunacionBeanLocal.getVacunadoresNoAsignados(nombreVacPrueba, "Puesto 2");
        Vacunador vacunador = null;
        for(Vacunador v: vacs) {
        	if(v.getCi() == 98765432) {
        		vacunador = v;
        		break;
        	}
        }
        
        puestoVacunacionBeanLocal.addAsignacion(vacunador, turno, puesto, LocalDate.of(2021, 7, 1), LocalDate.of(2021, 11, 30));
        List<PuestoVacunacion> p = puestoVacunacionBeanLocal.find(nombreVacPrueba, "Puesto 2");
        assertEquals(1, p.size());
        puesto = p.get(0);
        List<Asignacion> asignaciones =  puesto.getAsignaciones();
        assertEquals(1, asignaciones.size());
        assertEquals(98765432, asignaciones.get(0).getVacunador().getCi());
        assertEquals("Matutino", asignaciones.get(0).getTurno().getNombre());
        assertEquals("Puesto 2", asignaciones.get(0).getPuestoVacunacion().getNombrePuesto());
        assertEquals(nombreVacPrueba, asignaciones.get(0).getPuestoVacunacion().getVacunatorio().getNombre());
        
        vacs = puestoVacunacionBeanLocal.getVacunadoresNoAsignados(nombreVacPrueba, "Puesto 2");
        for(Vacunador vacu: vacs) {
        	if(vacu.getCi() == 98765432) {
        		fail("no se registro la asignacion del vacunador");
        	}
        }
    }
    
    @Test
    @InSequence(5)
    public void should_find_puestos() {
    	
    	Optional<Vacunatorio> vac = vacunatorioControllerLocal.find(nombreVacPrueba);
    	assertFalse(vac.isEmpty());
    	
    	List<PuestoVacunacionDTO> puestos = puestoVacunacionBeanLocal.getDTO(vac.get());
    	assertEquals(2, puestos.size());
    	
    }
    
    /*@Test
    @InSequence(6)
    public void should_find_vacunatorios_nearby() {
    	
    	GeometryFactory geomFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
    	
        vacunatorioControllerLocal.addVacunatorio("SI", "Mdeo", "Calle Facultad 3027", Departamento.Durazno, geomFactory.createPoint(new Coordinate(1,1.3)));
        vacunatorioControllerLocal.addVacunatorio("NO", "Mdeo", "Calle Facultad 3021", Departamento.Durazno, geomFactory.createPoint(new Coordinate(1.2,1.2)));
        
        List<Vacunatorio> vacCercanos = vacunatorioControllerLocal.getVacunatoriosCercanos(0.9, 1.1);
        assertEquals(2, vacCercanos.size());
        
        
    }*/
    
    
}