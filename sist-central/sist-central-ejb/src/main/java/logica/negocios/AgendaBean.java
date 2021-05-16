package logica.negocios;

import datos.dtos.AgendaDTO;
import datos.entidades.Agenda;
import datos.repositorios.AgendaRepositoryLocal;
import logica.creacion.Converter;
import logica.servicios.local.AgendaServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
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

    public AgendaDTO save(AgendaDTO agendaDTO) {
        Agenda agendaCreada = agendaConverter.convert(agendaDTO);
        agendaRepository.save(agendaCreada);
        return agendaDTOConverter.convert(agendaCreada);
    }
}