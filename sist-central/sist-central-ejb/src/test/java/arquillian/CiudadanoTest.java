package arquillian;

import datos.dtos.CiudadanoDTO;
import datos.exceptions.CiudadanoNoEncontradoException;
import datos.exceptions.CiudadanoRegistradoException;
import logica.creacion.CiudadanoDTOBuilder;
import logica.servicios.local.CiudadanoServiceLocal;
import plataformainteroperabilidad.Sexo;
import plataformainteroperabilidad.Trabajo;

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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class CiudadanoTest {

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
    private CiudadanoServiceLocal ciudadanoServiceLocal;



    @Test
    @InSequence(1)
    public void should_create_ciudadano() {
        CiudadanoDTOBuilder builder = new CiudadanoDTOBuilder();
        
        builder.setCi(50427444);
        builder.setEmail("agustin@email.com");
        builder.setNombre("Agustin Ruiz Diaz");
        builder.setVacunador(false);
        
        try {
			ciudadanoServiceLocal.save(builder.createCiudadanoDTO());
		} catch (CiudadanoRegistradoException e) {
			fail("agustin no deberia estar registrado ya");
		}
        ciudadanoServiceLocal.setSexoFechanacimiento(50427444, Sexo.HOMBRE, LocalDate.of(1999, 6, 1), Trabajo.TEXTIL);

        CiudadanoDTO usuario = null;
		try {
			usuario = ciudadanoServiceLocal.findByNombreCi(50427444);
		} catch (CiudadanoNoEncontradoException e) {
			fail("agustin deberia estar registrado");
		}
        
        assertNotNull(usuario);
        assertFalse(usuario.getVacunador());
        assertEquals(50427444, usuario.getCi());
        assertEquals("Agustin Ruiz Diaz", usuario.getNombre());
        assertEquals("agustin@email.com", usuario.getEmail());
        assertFalse(usuario.getVacunador());
                
    }
    
    @Test
    @InSequence(2)
    public void should_create_vacunador() {
        CiudadanoDTOBuilder builder = new CiudadanoDTOBuilder();
        
        builder.setCi(98765432);
        builder.setEmail("vacunador@email.com");
        builder.setNombre("Vac Un Ador");
        builder.setVacunador(true);
        
        try {
			ciudadanoServiceLocal.save(builder.createCiudadanoDTO());
		} catch (CiudadanoRegistradoException e) {
			fail("vacunador no deberia estar registrado ya");
		}

        CiudadanoDTO usuario = null;
		try {
			usuario = ciudadanoServiceLocal.findByNombreCi(98765432);
		} catch (CiudadanoNoEncontradoException e) {
			fail("vacunador deberia estar registrado");
		}
        
        assertNotNull(usuario);
        assertEquals(98765432, usuario.getCi());
        assertEquals("Vac Un Ador", usuario.getNombre());
        assertEquals("vacunador@email.com", usuario.getEmail());
        assertTrue(usuario.getVacunador());
    }

    @Test
    @InSequence(3)
    public void shouldnt_create_neither() {
        CiudadanoDTOBuilder builder = new CiudadanoDTOBuilder();
        
        builder.setCi(50427444);
        builder.setEmail("agustin@email.com");
        builder.setNombre("Agustin Ruiz Diaz");
        builder.setVacunador(false);
        
        try {
			ciudadanoServiceLocal.save(builder.createCiudadanoDTO());
			fail("agustin deberia estar registrado");
		} catch (CiudadanoRegistradoException e) {
			//todo ok
		}

        builder.setCi(98765432);
        builder.setEmail("vacunador@email.com");
        builder.setNombre("Vac Un Ador");
        builder.setVacunador(true);
        
        try {
			ciudadanoServiceLocal.save(builder.createCiudadanoDTO());
			fail("vacunador deberia estar registrado");
		} catch (CiudadanoRegistradoException e) {
			//todo ok
		}
    }
    
    @Test
    @InSequence(4)
    public void should_overwrite_ciudadanos() {
        
        List<CiudadanoDTO> usuarios = ciudadanoServiceLocal.find();
        //son 6 pq 4 se levantan cuando arranca el sistema
        assertEquals(6,usuarios.size());
        CiudadanoDTO ciudadano = null;
        CiudadanoDTO vacunador = null;
        for(CiudadanoDTO usuario: usuarios) {
        	if(usuario.getCi() == 98765432 && usuario.getVacunador()) {
        		vacunador = usuario;
        	}
        	else if(usuario.getCi() == 50427444){
        		ciudadano = usuario;
        	}
        }
        
        ciudadano.setEmail("agus@email.com");
        ciudadano.setNombre("Augustus Schneider");
        ciudadano.setVacunador(true);
        
        ciudadanoServiceLocal.overwriteCiudadano(ciudadano);
        
        vacunador.setEmail("peter@email.com");
        vacunador.setNombre("Peter Parker");
        vacunador.setVacunador(false);
        
        ciudadanoServiceLocal.overwriteCiudadano(vacunador);
        
        ciudadano = vacunador = null;
        // ciudadano es un vacunador y vacunador es un ciudadano, quedo confuso si
        try {
			vacunador = ciudadanoServiceLocal.findByNombreCi(98765432);
		} catch (CiudadanoNoEncontradoException e) {
			fail("este usuario deberia estar registrado");
		}
        
        assertNotNull(vacunador);
        assertEquals("peter@email.com", vacunador.getEmail());
        assertEquals(98765432, vacunador.getCi());
        assertEquals("Peter Parker", vacunador.getNombre());
        assertFalse(vacunador.getVacunador());
        
		ciudadano = ciudadanoServiceLocal.getVacunadorDTO(ciudadanoServiceLocal.findVacunador(50427444)) ;
		
		assertNotNull(ciudadano);
        assertEquals("agus@email.com", ciudadano.getEmail());
        assertEquals(50427444, ciudadano.getCi());
        assertEquals("Augustus Schneider", ciudadano.getNombre());
		

    }
    
    
}
