package arquillian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.ejb.EJB;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
import logica.servicios.local.VacunatorioControllerLocal;

@RunWith(Arquillian.class)
public class VacunatorioControllerTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap
        	//.create(WebArchive.class, "test.war")
        	.create(JavaArchive.class)
        	.addAsResource("META-INF/persistence.xml")
        	.addPackages(true, "datos", "logica")
            //.addClasses(Vacunatorio.class, PuestoVacunacion.class, VacunatorioControllerLocal.class, VacunatorioBean.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    VacunatorioBean vacunatorioControllerLocal;

    @Test
    public void should_create_vacunatorio() {
    	String nombreVacPrueba = "VacunatorioPrueba";
        vacunatorioControllerLocal.addVacunatorio(nombreVacPrueba, "Mdeo", "Calle Facultad 3027", Departamento.Artigas);
        Vacunatorio vac = vacunatorioControllerLocal.find(nombreVacPrueba).get();
        assertEquals(vac.getCiudad(), "Mdeo");
        assertEquals(vac.getDepartamento(), Departamento.Artigas);
        assertEquals(vac.getDireccion(), "Calle Facultad 3027");
        assertEquals(vac.getPuestosVacunacion().isEmpty(), true);
    }
}