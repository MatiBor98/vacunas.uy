package arquillian;

import datos.dtos.VacunatorioTieneAgendaDTO;
import datos.entidades.Departamento;
import datos.entidades.Estado;
import datos.entidades.Intervalo;
import datos.entidades.Reserva;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.remoto.AgendaServiceRemote;
import logica.servicios.remoto.ReservaServiceRemote;
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
import plataformainteroperabilidad.Trabajo;

import javax.ejb.EJB;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class AgendaTest {

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
    private ReservaServiceRemote reservaServiceRemote;
    @EJB
    private AgendaServiceRemote agendaServiceRemote;
    @EJB
    private AgendaServiceLocal agendaServiceLocal;

    @Test
    @InSequence(7)
    public void should_cancel_reserva() {

        assertEquals(2, reservaServiceRemote.listarCount(52050756).intValue());
        Reserva reserva = null;
        for (Reserva r : reservaServiceRemote.listar(0, 5, 52050756)) {
            if (r.getIntervalo().getAgenda().getEtapa().getVacuna().getNombre().equals("Coronavac")) {
                reserva = r;
                break;

            }
        }

        assertNotNull(reserva);
        reservaServiceRemote.cancelar(52050756, reserva.getCodigo());

        //deben haberse cancelado las 2 de coronavac
        assertEquals(2, reservaServiceRemote.listarCount(52050756).intValue());
        List<Reserva> reservas = reservaServiceRemote.listar(0, 5, 52050756);
        assertEquals(Estado.CANCELADA, reservas.get(0).getEstado());
        assertEquals(Estado.CANCELADA, reservas.get(1).getEstado());


    }

    @Test
    @InSequence(8)
    public void should_confirm_reserva() {
        List<VacunatorioTieneAgendaDTO> agendasVacunatorios = agendaServiceRemote.findAgendasParaCiudadanoPorDepartamento("Corona Virus", 50, Trabajo.SALUD, Departamento.Montevideo);
        //3 agendas creadas al iniciar el sistema y una en estos tests
        assertEquals(4, agendasVacunatorios.size());
        boolean existeAgenda4 = false;
        for (VacunatorioTieneAgendaDTO vacAg : agendasVacunatorios) {
            if (vacAg.getAgenda().getId() == 4) {
                existeAgenda4 = true;
            }
        }
        assertTrue(existeAgenda4);
        //se generan 4 al iniciar el sistema
        assertEquals(5, agendaServiceLocal.findByNombrePlan("Plan vacunacion covid 19").size());

        List<Intervalo> intervalos = null;
        try {
            intervalos = agendaServiceRemote.getIntervalos(4, LocalDate.of(2021, 6, 21));
        } catch (RuntimeException e) {
            fail("la fecha mandada tiene que ser de un lunes");
        }

        try {
            intervalos = agendaServiceRemote.getIntervalos(4, LocalDate.of(2021, 6, 22));
        } catch (RuntimeException e) {
            //todo ok
        }

        assertNotNull(intervalos);
        assertTrue(intervalos.size() > 0);
        Intervalo intervalo = intervalos.get(0);
        List<Reserva> reservas = reservaServiceRemote.efectuarReserva(intervalo, 52050756);
        System.out.println("Se reserva para el intervalo" + intervalo.getId());
        //tienen q ser 2 pq son la Pfizer y esta vacuna asi lo pide
        assertEquals(2, reservas.size());
        assertEquals(52050756, reservas.get(0).getCiudadano().getCi());
        assertEquals(52050756, reservas.get(1).getCiudadano().getCi());
        assertEquals(Estado.PENDIENTE, reservas.get(0).getEstado());
        assertTrue(17 <= reservas.get(1).getIntervalo().getFechayHora().getDayOfYear() - reservas.get(0).getIntervalo().getFechayHora().getDayOfYear());
        assertEquals(4, reservas.get(0).getIntervalo().getAgenda().getId());
        assertTrue(reservas.get(0).getIntervalo().getReservas().contains(reservas.get(0)));

        assertEquals(4, reservaServiceRemote.listarCount(52050756).intValue());

    }
}
