package arquillian;

import datos.dtos.AgendaDTO;
import datos.dtos.AgendaDTO2;
import datos.dtos.EtapaDTO;
import datos.dtos.InformacionPosiblesIntervalosDTO;
import datos.dtos.ReservaDTO;
import datos.dtos.VacunatorioTieneAgendaDTO;
import datos.dtos.VacunatorioTieneAgendaParaEtapaDTO;
import datos.entidades.Agenda;
import datos.entidades.Departamento;
import datos.entidades.Estado;
import datos.entidades.Intervalo;
import datos.entidades.Reserva;
import datos.entidades.Vacunatorio;
import logica.creacion.AgendaDTOBuilder;
import logica.creacion.InformacionPosiblesIntervalosDTOBuilder;
import logica.negocios.ReservaBean;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.EtapaController;
import logica.servicios.local.IntervaloServiceLocal;
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

    @EJB
    private ReservaBean reservaService;

    @EJB
    private IntervaloServiceLocal intervaloServiceLocal;
    
    @EJB
    private VacunatorioControllerLocal vacunatorioController;

    @Test
    @InSequence(1)
    public void should_create_an_agenda() {
        AgendaDTOBuilder builder = new AgendaDTOBuilder();

        Map<DayOfWeek, InformacionPosiblesIntervalosDTO> informaciones = new HashMap<>() {{
            for (DayOfWeek day : DayOfWeek.values()) {
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
                .setNombre("Matutino")
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
        List<EtapaDTO> etapas = etapaController.find("Coronavirus", 50, null);
        assertEquals(etapas.size(), 1);
        assertEquals(etapas.get(0).getId(), 3);
    }

    @Test
    @InSequence(4)
    public void should_find_etapa_for_user_info_cv_20_salud() {
        List<EtapaDTO> etapas = etapaController.find("Coronavirus", 20, Trabajo.SALUD);
        assertEquals(etapas.size(), 3);
        assertEquals(etapas.get(0).getId(), 1);
        assertEquals(etapas.get(1).getId(), 2);
        assertEquals(etapas.get(2).getId(), 3);

    }

    @Test
    @InSequence(5)
    public void should_find_etapa_for_user_info_cv_20_enenansa() {
        List<EtapaDTO> etapas = etapaController.find("Coronavirus", 20, Trabajo.EDUCACION);
        assertEquals(etapas.size(), 2);
        assertEquals(etapas.get(0).getId(), 2);
        assertEquals(etapas.get(1).getId(), 3);

    }

    @Test
    @InSequence(6)
    public void should_find_etapa_for_user_info_cv_50_salud() {
        List<EtapaDTO> etapas = etapaController.find("Coronavirus", 50, Trabajo.SALUD);
        assertEquals(etapas.size(), 3);
        assertEquals(etapas.get(0).getId(), 1);
        assertEquals(etapas.get(1).getId(), 2);
        assertEquals(etapas.get(2).getId(), 3);
    }


    @Test
    @InSequence(7)
    public void should__not_cancel_reserva() {

    	
    	assertEquals(3, reservaService.listarCount(52050756).intValue());
    	Reserva reserva = null;
    	for(Reserva r: reservaService.listar(0, 5, 52050756)) {
    		if(r.getIntervalo().getAgenda().getEtapa().getVacuna().getNombre().equals("Pfizer")) {
    			reserva = r;
    			break;
    			
    		}
    	}
    	
    	assertNotNull(reserva);
    	reservaService.cancelar(52050756, reserva.getCodigo());
    	
    	//no deben haberse cancelado las 2 de pfizer
    	assertEquals(3, reservaService.listarCount(52050756).intValue());
    	List<Reserva> reservas = reservaService.listar(0, 5, 52050756);
    	assertEquals(Estado.VACUNADO, reservas.get(0).getEstado());
    	assertEquals(Estado.VACUNADO, reservas.get(1).getEstado());
    	assertEquals(Estado.VACUNADO, reservas.get(2).getEstado());


    }
    
    @Test
    @InSequence(8)
    public void should_create_reserva() {
        List<VacunatorioTieneAgendaDTO> agendasVacunatorios = agendaServiceLocal.findAgendasParaCiudadanoPorDepartamento("Coronavirus", 50, Trabajo.SALUD, Departamento.Montevideo);
        //3 agendas creadas al iniciar el sistema y una en estos tests
        assertEquals(1, agendasVacunatorios.size());
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
            intervalos = intervaloServiceLocal.getIntervalosByAgendaAndSemana(4, LocalDate.of(2021, 6, 21));
        } catch (RuntimeException e) {
            fail("la fecha mandada tiene que ser de un lunes");
        }

        try {
            intervalos = intervaloServiceLocal.getIntervalosByAgendaAndSemana(4, LocalDate.of(2021, 6, 22));
        } catch (RuntimeException e) {
            //todo ok
        }

        assertNotNull(intervalos);
        assertTrue(intervalos.size() > 0);
        Intervalo intervalo = intervalos.get(0);
        List<Reserva> reservas = reservaService.efectuarReserva(intervalo, 50550419);
        System.out.println("Se reserva para el intervalo" + intervalo.getId());
        //tienen q ser 2 pq son la Pfizer y esta vacuna asi lo pide
        assertEquals(2, reservas.size());
        assertEquals(50550419, reservas.get(0).getCiudadano().getCi());
        assertEquals(50550419, reservas.get(1).getCiudadano().getCi());
        assertEquals(Estado.PENDIENTE, reservas.get(0).getEstado());
        assertTrue(17 <= reservas.get(1).getIntervalo().getFechayHora().getDayOfYear() - reservas.get(0).getIntervalo().getFechayHora().getDayOfYear());
        assertEquals(4, reservas.get(0).getIntervalo().getAgenda().getId());
        assertTrue(reservas.get(0).getIntervalo().getReservas().contains(reservas.get(0)));

        assertEquals(7, reservaService.listarCount(50550419).intValue());
        
        AgendaDTO2 agendadto = agendaServiceLocal.getAgendaDTO2(intervalo.getAgenda());
        assertEquals("Plan vacunacion covid 19",agendadto.getEtapa().getPlanVac().getNombre());
        assertEquals("COSEM Punta Carretas" ,agendadto.getTurno().getVacunatorio());
         
        Reserva reserva = null;
    	for(Reserva r: reservaService.listar(0, 40, 50550419)) {
    		if(r.getIntervalo().getAgenda().getEtapa().getVacuna().getNombre().equals("Pfizer")) {
    			reserva = r;
    			break;
    		}
    	}
    	assertNotNull(reserva);
    	reservaService.cancelarVacuna(reserva.getCodigo());
    	assertEquals(7, reservaService.listarCount(50550419).intValue());
    	reservas = reservaService.listar(0, 5, 50550419);
    	for(Reserva r: reservas) {
    		if(r.getIntervalo().getAgenda().getEtapa().getVacuna().getNombre().equals("Pfizer")) {
    	    	assertEquals(Estado.CANCELADA, r.getEstado());
    		}
    	}
    }
    
    @Test
    @InSequence(9)
    public void should_cancel_reserva() {

    	
    	assertEquals(7, reservaService.listarCount(50550419).intValue());
    	Reserva reserva = null;
    	for(Reserva r: reservaService.listar(0, 10, 50550419)) {
    		if(r.getIntervalo().getAgenda().getEtapa().getVacuna().getNombre().equals("Gripevac")) {
    			reserva = r;
    			break;
    			
    		}
    	}
    	
    	assertNotNull(reserva);
    	reservaService.cancelar(50550419, reserva.getCodigo());
    	
    	//debe haberse cancelado las 1 de gripevac
    	assertEquals(7, reservaService.listarCount(50550419).intValue());
    	List<Reserva> reservas = reservaService.listar(0, 5, 50550419);
    	assertEquals(Estado.CANCELADA, reservas.get(0).getEstado());
    	
    	
    	/*for(Reserva r: reservaService.listar(0, 40, 50550419)) {
    		if(r.getIntervalo().getAgenda().getEtapa().getVacuna().getNombre().equals("Pfizer")) {
    			reserva = r;
    			break;
    		}
    	}
    	assertNotNull(reserva);
    	reservaService.cancelarVacuna(reserva.getCodigo());
    	assertEquals(7, reservaService.listarCount(50550419).intValue());
    	reservas = reservaService.listar(0, 5, 50550419);
    	for(Reserva r: reservas) {
    		if(r.getIntervalo().getAgenda().getEtapa().getVacuna().getNombre().equals("Pfizer")) {
    	    	assertEquals(Estado.CANCELADA, r.getEstado());
    		}
    	}*/

    }
    
    @Test
    @InSequence(10)
    public void should_change_state_vacunado() {
    	//Reserva 10, Pendiente, 50550419(Nico), intervalo 8(Artigas, Gripevac)
    	reservaService.confirmarVacuna(10, String.valueOf(4000));
    	List<Reserva> reservas = reservaService.listar(0, 5, 50550419);
    	Reserva reservaConfirmada = null;
    	for (Reserva r: reservas) {
    		if(r.getCodigo() == 10) {
    			reservaConfirmada = r;
    			break;
    		}
    	}
    	assertNotNull(reservaConfirmada);
    	assertEquals(50550419, reservaConfirmada.getCiudadano().getCi());
    	assertEquals("Gripevac", reservaConfirmada.getIntervalo().getAgenda().getEtapa().getVacuna().getNombre());
    	assertEquals(8, reservaConfirmada.getIntervalo().getId().intValue());
    	assertEquals("4000",reservaConfirmada.getLote());
    	assertEquals(Estado.VACUNADO, reservaConfirmada.getEstado());
    }
    
    @Test
    @InSequence(11)
    public void should_find_reservas() {
    	Optional<Vacunatorio> vacunatorio = vacunatorioController.find("COSEM Punta Carretas");
    	assertFalse(vacunatorio.isEmpty());
    	
    	List<ReservaDTO> reservas = reservaService.getReservasDTO(vacunatorio.get());
    	assertEquals(10, reservas.size());
    }
    
    @Test
    @InSequence(12)
    public void statistics() {
    	assertEquals(3, reservaService.findAllDosisDadas("Coronavirus", "Pfizer", 1).size());
    	assertEquals(1, reservaService.findAllDosisDadas("Gripe 2021", "Gripevac", 4, LocalDate.of(2021, 6, 7), LocalDate.of(2021, 6, 7)).size());
    	Map<String, Integer> vacunasDeps = reservaService.getDosisPorDepartamentos("Gripe 2021", "Gripevac", 4, LocalDate.of(2021, 6, 3), LocalDate.of(2022, 6, 19));
    	for(String dep: vacunasDeps.keySet()) {
    		if(dep.equals("Montevideo") || dep.equals("Artigas")) {
    			assertEquals(1, vacunasDeps.get(dep).intValue());
    		}
    		else {
    			assertEquals(0, vacunasDeps.get(dep).intValue());
    		}
    	}
    }
    
    @Test
    @InSequence(13)
    public void find() {
    	
    	List<VacunatorioTieneAgendaParaEtapaDTO> agendas = agendaServiceLocal.find(0, 10);
    	assertEquals(9, agendas.size());
    	
    	agendas = agendaServiceLocal.findByNombrePlan(0, 2, "Plan vacunacion covid 19");
    	assertEquals(2, agendas.size());
    }
}
