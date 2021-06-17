package logica.negocios;

import datos.dtos.AgendaDTO;
import datos.dtos.VacunatorioDTO;
import datos.dtos.VacunatorioTieneAgendaDTO;
import datos.entidades.*;
import datos.repositorios.AgendaRepositoryLocal;
import datos.repositorios.CiudadanoRepositoryLocal;
import datos.repositorios.IntervaloRepository;
import datos.repositorios.ReservaRepository;
import logica.creacion.Converter;
import logica.servicios.local.AgendaServiceLocal;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Stateless
public class AgendaBean implements AgendaServiceLocal {
    @EJB
    private AgendaRepositoryLocal agendaRepository;

    @Inject
    private Converter<AgendaDTO, Agenda> agendaConverter;

    @Inject
    private Converter<Agenda, AgendaDTO> agendaDTOConverter;

    public AgendaBean() {
    }

    @Override
    public List<AgendaDTO> find() {
        return agendaRepository.find().parallelStream().map(agendaDTOConverter::convert).collect(Collectors.toList());
    }

    @Override
    public Optional<AgendaDTO> find(int id) {
        return agendaRepository.find(id).map(agendaDTOConverter::convert);
    }

    @Override
    public List<AgendaDTO> findByNombrePlan(String criterio) {
        return agendaRepository.findByNombrePlan(criterio).parallelStream().map(agendaDTOConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public AgendaDTO save(AgendaDTO agendaDTO) {
        Agenda agendaCreada = agendaConverter.convert(agendaDTO);
        agendaRepository.save(agendaCreada);
        return agendaDTOConverter.convert(agendaCreada);
    }
}