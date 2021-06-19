package arquillian;

import datos.dtos.UsuarioBackOfficeDTO;
import datos.exceptions.EmailNoRegistradoException;
import datos.exceptions.EmailRegistradoException;
import datos.exceptions.PasswordIncorrectaException;
import logica.servicios.local.UsuariosBackOfficeBeanLocal;
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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class UsuariosBackOfficeTest {

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
    UsuariosBackOfficeBeanLocal usuarioBackOfficeBean;
   
    

    @Test
    @InSequence(1)
    public void should_create_administrador() {
    	try {
			usuarioBackOfficeBean.addBOUser("admin@email.com", "admin", "Administrador");
		} catch (EmailRegistradoException e) {
			fail("este usuario no deberia aparecer como registrado");
		}
    	List<UsuarioBackOfficeDTO> usuarios = usuarioBackOfficeBean.usersList();
    	UsuarioBackOfficeDTO admin = null;
    	for(UsuarioBackOfficeDTO usuario: usuarios) {
    		if(usuario.getEmail().equals("admin@email.com")) {
    			admin = usuario;
    			break;
    		}
    	}
    	assertNotEquals(null, admin);
    	assertEquals("admin@email.com", admin.getEmail());
    	assertEquals("administrador", admin.getRol());
        System.out.print("admin creado");
    }
    
    @Test
    @InSequence(2)
    public void should_create_autoridad() {
    	try {
			usuarioBackOfficeBean.addBOUser("auto@email.com", "auto", "Autoridad");
		} catch (EmailRegistradoException e) {
			fail("este usuario no deberia aparecer como registrado");
		}
    	List<UsuarioBackOfficeDTO> usuarios = usuarioBackOfficeBean.usersList();
    	UsuarioBackOfficeDTO auto = null;
    	for(UsuarioBackOfficeDTO usuario: usuarios) {
    		if(usuario.getEmail().equals("auto@email.com")) {
    			auto = usuario;
    			break;
    		}
    	}
    	assertNotEquals(auto, null);
    	assertEquals("auto@email.com", auto.getEmail());
    	assertEquals("autoridad", auto.getRol());
        System.out.print("auto creado");
    }
    
    @Test
    @InSequence(3)
    public void should_authenticate() {
    	try {
			usuarioBackOfficeBean.auntenticarUsuario("admin@email.com", "admin");
		} catch (EmailNoRegistradoException e) {
			fail("el usuario no esta registrado");
		} catch (PasswordIncorrectaException e) {
			fail("la password es incorrecta");
		}
    	
		try {
			usuarioBackOfficeBean.auntenticarUsuario("ad@email.com", "admin");
			fail("el usuario deberia no estar registrado");
		} catch (EmailNoRegistradoException e) {
			//todo ok
    	}
    	catch
    	(PasswordIncorrectaException e) {
			fail("la otra excecpcion deberia enviarse antes");
		}
		
		try {
			usuarioBackOfficeBean.auntenticarUsuario("admin@email.com", "adm");
			fail("la password deberia ser incorrecta");
		} catch (EmailNoRegistradoException e) {
			fail("esta excepcion no deberia enviarse");
    	}
    	catch
    	(PasswordIncorrectaException e) {
			//todo ok
		}
    		
    	
    }

    @Test
    @InSequence(4)
    public void should_overwrite() {
    	List<UsuarioBackOfficeDTO> usuarios = usuarioBackOfficeBean.usersList();
    	UsuarioBackOfficeDTO nuevoAdmin = null, nuevoAuto = null;

    	for(UsuarioBackOfficeDTO usuario: usuarios) {
    		if(usuario.getEmail().equals("admin@email.com")) {
    			nuevoAuto = usuario;
    		}
    		if(usuario.getEmail().equals("auto@email.com")) {
    			nuevoAdmin = usuario;
    		}
    	}
    	assertEquals(3, usuarios.size()); // hay 3 pq el sistema crea uno al iniciar
    	
    	assertNotEquals(null, nuevoAdmin);
    	assertNotEquals(null, nuevoAuto);
    	
    	assertEquals("auto@email.com", nuevoAdmin.getEmail());
    	assertEquals("autoridad", nuevoAdmin.getRol());
    	
    	assertEquals("admin@email.com", nuevoAuto.getEmail());
    	assertEquals("administrador" , nuevoAuto.getRol());
    	
    	nuevoAdmin.setRol("administrador");
    	nuevoAuto.setRol("autoridad");
    	
    	usuarioBackOfficeBean.overwriteUsuarioBackOffice(nuevoAuto);
    	usuarioBackOfficeBean.overwriteUsuarioBackOffice(nuevoAdmin);
    	
    	usuarios = usuarioBackOfficeBean.usersList();
    	assertEquals(3, usuarios.size());
    	
    	for(UsuarioBackOfficeDTO usuario: usuarios) {
    		if(usuario.getEmail().equals("admin@email.com")) {
    			assertEquals("autoridad", usuario.getRol());
    		}
    		if(usuario.getEmail().equals("auto@email.com")) {
    			assertEquals("administrador", usuario.getRol());
    		}
    	}
    }
    
}