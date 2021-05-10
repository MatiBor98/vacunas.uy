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
import java.util.stream.Collectors;

@Stateless
public class AgendaBean implements AgendaServiceLocal {
    @EJB
    private AgendaRepositoryLocal agendaRepository;

    @Inject
    private Converter<AgendaDTO, Agenda> agendaDTOConverter;

    @Inject
    private Converter<Agenda, AgendaDTO> agendaConverter;

    public AgendaBean() {
    }

    @Override
    public List<AgendaDTO> find() {
        return agendaRepository.find().stream().map(agendaConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<AgendaDTO> findByNombrePlan(String criterio) {
        return agendaRepository.findByNombrePlan(criterio).stream().map(agendaConverter::convert)
                .collect(Collectors.toList());
    }

    public void save(AgendaDTO agendaDTO) {
        agendaRepository.save(agendaDTOConverter.convert(agendaDTO));
    }
}