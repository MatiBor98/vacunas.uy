package logica.negocios;

import datos.entidades.Departamento;
import datos.repositorios.ReservaRepository;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.Map;

@Stateless
@LocalBean
public class ReservaBean {
    @EJB
    private ReservaRepository reservaRepository;

    public Map<String, Integer> getDosisPorDepartamentos(String enfermedad, String vacuna, int etapa) {
        Map<String, Integer> dosisDepartamentos = new HashMap<>();
        for (Departamento dep : Departamento.values()) {
            dosisDepartamentos.put(dep.name(), reservaRepository
                    .findCantidadDosisDadasDepartamento(dep, enfermedad, vacuna, etapa).size());
        }

        return dosisDepartamentos;
    }

    public Integer findVacunadosHoy(String enfermedad, String vacuna, int etapa) {
        return reservaRepository.findVacunadosHoy(enfermedad, vacuna, etapa);
    }

    public Integer findAgendadosProximos(String enfermedad, String vacuna, int etapa) {
        return reservaRepository.findAgendadosProximos(enfermedad, vacuna, etapa);
    }

}
