package arquillian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalTime;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import datos.entidades.Departamento;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Vacunatorio;
import logica.negocios.VacunatorioBean;
import logica.servicios.local.PuestoVacunacionBeanLocal;
import logica.servicios.local.VacunatorioControllerLocal;

@RunWith(Arquillian.class)
public class VacunatorioControllerTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
        	.addAsResource("META-INF/persistence.xml")
        	.addPackages(true, "datos", "logica")
            //.addClasses(Vacunatorio.class, PuestoVacunacion.class, VacunatorioControllerLocal.class, VacunatorioBean.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        	//.addAsResource();
    }

    @EJB
    VacunatorioControllerLocal vacunatorioControllerLocal;
   
    
    String nombreVacPrueba = "VacunatorioPruebaXYZ0123";

    @Test
    @InSequence(1)
    public void should_create_vacunatorio() {
        vacunatorioControllerLocal.addVacunatorio(nombreVacPrueba, "Mdeo", "Calle Facultad 3027", Departamento.Artigas);
        Vacunatorio vac = vacunatorioControllerLocal.findWithEverything(nombreVacPrueba).get();
        assertEquals(vac.getCiudad(), "Mdeo");
        assertEquals(vac.getDepartamento(), Departamento.Artigas);
        assertEquals(vac.getDireccion(), "Calle Facultad 3027");
        assertEquals(vac.getPuestosVacunacion().isEmpty(), true);
        System.out.print("xd");
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
    @InSequence(2)
    public void should_add_Turno() {
    	vacunatorioControllerLocal.addTurno("Matutino", LocalTime.of(8, 0), LocalTime.of(14, 0), nombreVacPrueba);
    	vacunatorioControllerLocal.addTurno("Vespertino", LocalTime.of(16, 0), LocalTime.of(20, 0), nombreVacPrueba);
    	
        Vacunatorio vac = vacunatorioControllerLocal.findWithEverything(nombreVacPrueba).get();
        assertEquals(2, vac.getPuestosVacunacion().size());

    }
    
}