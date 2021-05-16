package arquillian;

import datos.dtos.AgendaDTO;
import datos.dtos.EtapaDTO;
import datos.dtos.InformacionPosiblesIntervalosDTO;
import datos.entidades.Trabajos;
import logica.creacion.AgendaDTOBuilder;
import logica.creacion.InformacionPosiblesIntervalosDTOBuilder;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.EtapaController;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
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
        return ShrinkWrap
                .create(JavaArchive.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/load.sql")
                .addPackages(true, "datos", "logica")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private AgendaServiceLocal agendaServiceLocal;

    @EJB
    private EtapaController etapaController;

    @Test
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
    public void shouldnt_find_etapa_for_user_info_cv_20_null() {
        List<EtapaDTO> etapas = etapaController.find("Corona Virus", 20, null);
        assertTrue(etapas.isEmpty());
    }

    @Test
    public void should_find_etapa_for_user_info_cv_50_null() {
        List<EtapaDTO> etapas = etapaController.find("Corona Virus", 50, null);
        assertEquals(etapas.size(), 1);
        assertEquals(etapas.get(0).getId(), 3);
    }

    @Test
    public void should_find_etapa_for_user_info_cv_20_salud() {
        List<EtapaDTO> etapas = etapaController.find("Corona Virus", 20, Trabajos.Salud);
        assertEquals(etapas.size(), 2);
        assertEquals(etapas.get(0).getId(), 1);
        assertEquals(etapas.get(1).getId(), 2);
    }

    @Test
    public void should_find_etapa_for_user_info_cv_20_enenansa() {
        List<EtapaDTO> etapas = etapaController.find("Corona Virus", 20, Trabajos.Educacion);
        assertEquals(etapas.size(), 1);
        assertEquals(etapas.get(0).getId(), 2);
    }

    @Test
    public void should_find_etapa_for_user_info_cv_50_salud() {
        List<EtapaDTO> etapas = etapaController.find("Corona Virus", 50, Trabajos.Salud);
        assertEquals(etapas.size(), 3);
        assertEquals(etapas.get(0).getId(), 1);
        assertEquals(etapas.get(1).getId(), 2);
        assertEquals(etapas.get(2).getId(), 3);
    }
}
