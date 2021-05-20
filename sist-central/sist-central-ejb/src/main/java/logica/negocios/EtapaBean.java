package logica.negocios;

import datos.dtos.EtapaDTO;
import datos.entidades.Etapa;
import datos.entidades.Trabajos;
import datos.repositorios.EtapaRepository;
import logica.creacion.Converter;
import logica.servicios.local.EtapaController;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class EtapaBean  implements EtapaController {
    @EJB
    private EtapaRepository etapaRepository;

    @Inject
    private Converter<EtapaDTO, Etapa> etapaDTOEtapaConverter;

    @Inject
    private Converter<Etapa, EtapaDTO> etapaEtapaDTOConverter;

    public EtapaBean() {}

    public EtapaDTO save(EtapaDTO etapa) {
        Etapa nuevaEtapa = etapaDTOEtapaConverter.convert(etapa);
        etapaRepository.save(nuevaEtapa);
        return etapaEtapaDTOConverter.convert(nuevaEtapa);
    }

    @Override
    public Optional<EtapaDTO> find(int id) {
        return etapaRepository.find(id).map(etapaEtapaDTOConverter::convert);
    }

    @Override
    public List<EtapaDTO> find(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos) {
        List<Etapa> etapas = etapaRepository.find(nombreEnfermedad, edadCiudadano, trabajos);
        return etapas.parallelStream().map(etapaEtapaDTOConverter::convert).collect(Collectors.toList());
    }

    @Override
    public boolean habilidadoCiudadano(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos) {
        //TODO: hacer esto en la consulta para mejorar
        return !etapaRepository.find(nombreEnfermedad, edadCiudadano, trabajos).isEmpty();
    }
}
