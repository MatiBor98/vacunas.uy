package arquillian;

import datos.dtos.AgendaDTO;
import datos.dtos.EtapaDTO;
import datos.dtos.InformacionPosiblesIntervalosDTO;
import datos.dtos.VacunatorioTieneAgendaDTO;
import datos.entidades.Departamento;
import datos.entidades.Estado;
import datos.entidades.Intervalo;
import datos.entidades.Reserva;
import logica.creacion.AgendaDTOBuilder;
import logica.creacion.InformacionPosiblesIntervalosDTOBuilder;
import logica.negocios.ReservaBean;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.EtapaController;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private AgendaServiceLocal agendaServiceLocal;

    @EJB
    private EtapaController etapaController;

    @EJB(lookup = "java:global/sist-central-agendar/sist-central-agendar-ejb/ReservaBean!logica.servicios.remoto.ReservaServiceRemote")
    private ReservaServiceRemote reservaService;

    @Test
    @InSequence(1)
    public void should_create_an_agenda() {
        AgendaDTOBuilder builder = new AgendaDTOBuilder();

        Map<DayOfWeek, InformacionPosiblesIntervalosDTO> informaciones = new HashMap<>() {{
            for(DayOfWeek day : DayOfWeek.values()) {
                InformacionPosiblesIntervalosDTOBuilder infoBuilder = new InformacionPosiblesIntervalosDTOBuilder();
                put(day, infoBuilder.setCapacidadPorTurno(15)
                        .setInicio(LocalTime.parse("08:00"))
                        .setFin(LocalTime.parse("12:00"))
                        .setMinutosTurno(10)
                        .createHoraInicioFinDTO());
            }
        }};

        AgendaDTO agendaDTO = builder.setEtapaId(1)
                .setTurnoId(1)
                .setInicio(LocalDate.parse("2021-05-14"))
                .setFin(LocalDate.parse("2021-05-14").plusYears(1))
                .setHorarioPorDia(informaciones)
                .createAgendaDTO();

        AgendaDTO agendaId = agendaServiceLocal.save(agendaDTO);
        Optional<AgendaDTO> agendaObtenida = agendaServiceLocal.find(agendaId.getId());

        assertNotNull(agendaObtenida);
        assertTrue(agendaObtenida.isPresent());
        assertEquals(agendaId, agendaObtenida.get());
    }

    @Test
    @InSequence(2)
    public void shouldnt_find_etapa_for_user_info_cv_20_null() {
        List<EtapaDTO> etapas = etapaController.find("Corona Virus", 20, null);
        assertTrue(etapas.isEmpty());
    }

    @Test
    @InSequence(3)
    public void should_find_etapa_for_user_info_cv_50_null() {
        List<EtapaDTO> etapas = etapaController.find("Corona Virus", 50, null);
        assertEquals(etapas.size(), 1);
        assertEquals(etapas.get(0).getId(), 3);
    }

    @Test
    @InSequence(4)
    public void should_find_etapa_for_user_info_cv_20_salud() {
        List<EtapaDTO> etapas = etapaController.find("Corona Virus", 20, Trabajo.SALUD);
        assertEquals(etapas.size(), 2);
        assertEquals(etapas.get(0).getId(), 1);
        assertEquals(etapas.get(1).getId(), 2);
    }

    @Test
    @InSequence(5)
    public void should_find_etapa_for_user_info_cv_20_enenansa() {
        List<EtapaDTO> etapas = etapaController.find("Corona Virus", 20, Trabajo.EDUCACION);
        assertEquals(etapas.size(), 1);
        assertEquals(etapas.get(0).getId(), 2);
    }

    @Test
    @InSequence(6)
    public void should_find_etapa_for_user_info_cv_50_salud() {
        List<EtapaDTO> etapas = etapaController.find("Corona Virus", 50, Trabajo.SALUD);
        assertEquals(etapas.size(), 3);
        assertEquals(etapas.get(0).getId(), 1);
        assertEquals(etapas.get(1).getId(), 2);
        assertEquals(etapas.get(2).getId(), 3);
    }
    
    
    @Test
    @InSequence(7)
    public void should_cancel_reserva() {
    	
    	assertEquals(2, reservaService.listarCount(52050756).intValue());
    	Reserva reserva = null;
    	for(Reserva r: reservaService.listar(0, 5, 52050756)) {
    		if(r.getIntervalo().getAgenda().getEtapa().getVacuna().getNombre().equals("Coronavac")) {
    			reserva = r;
    			break;
    			
    		}
    	}
    	
    	assertNotNull(reserva);
    	reservaService.cancelar(52050756, reserva.getCodigo());
    	
    	//deben haberse cancelado las 2 de coronavac
    	assertEquals(2, reservaService.listarCount(52050756).intValue());
    	List<Reserva> reservas = reservaService.listar(0, 5, 52050756);
    	assertEquals(Estado.CANCELADA, reservas.get(0).getEstado());
    	assertEquals(Estado.CANCELADA, reservas.get(1).getEstado());


    }
    
    @Test
    @InSequence(8)
    public void should_confirm_reserva() {
    	List<VacunatorioTieneAgendaDTO> agendasVacunatorios = agendaServiceLocal.findAgendasParaCiudadanoPorDepartamento("Corona Virus", 50, Trabajo.SALUD, Departamento.Montevideo);
    	//3 agendas creadas al iniciar el sistema y una en estos tests
    	assertEquals(4, agendasVacunatorios.size());
    	boolean existeAgenda4 = false;
    	for(VacunatorioTieneAgendaDTO vacAg: agendasVacunatorios) {
    		if(vacAg.getAgenda().getId() == 4) {
    			existeAgenda4 = true;
    		}
    	}
    	assertTrue(existeAgenda4);
    	//se generan 4 al iniciar el sistema
    	assertEquals(5, agendaServiceLocal.findByNombrePlan("Plan vacunacion covid 19").size());
    	
    	List<Intervalo> intervalos = null;
    	try {
        	intervalos = agendaServiceLocal.getIntervalos(4, LocalDate.of(2021, 6, 21));
    	}
    	catch(RuntimeException e){
    		fail("la fecha mandada tiene que ser de un lunes");
    	}
    	
    	try {
        	intervalos = agendaServiceLocal.getIntervalos(4, LocalDate.of(2021, 6, 22));
    	}
    	catch(RuntimeException e){
    		//todo ok
    	}
    	
    	assertNotNull(intervalos);
    	assertTrue(intervalos.size() > 0);
    	Intervalo intervalo = intervalos.get(0);
    	List<Reserva> reservas = agendaServiceLocal.efectuarReserva(intervalo, 52050756);
    	System.out.println("Se reserva para el intervalo" + intervalo.getId());
    	//tienen q ser 2 pq son la Pfizer y esta vacuna asi lo pide
    	assertEquals(2, reservas.size());
    	assertEquals(52050756, reservas.get(0).getCiudadano().getCi());
    	assertEquals(52050756, reservas.get(1).getCiudadano().getCi());
    	assertEquals(Estado.PENDIENTE , reservas.get(0).getEstado());
    	assertTrue(17 <= reservas.get(1).getIntervalo().getFechayHora().getDayOfYear() - reservas.get(0).getIntervalo().getFechayHora().getDayOfYear());
    	assertEquals(4, reservas.get(0).getIntervalo().getAgenda().getId());
    	assertTrue(reservas.get(0).getIntervalo().getReservas().contains(reservas.get(0)));
    	
    	assertEquals(4, reservaService.listarCount(52050756).intValue());
    	
    }
}
