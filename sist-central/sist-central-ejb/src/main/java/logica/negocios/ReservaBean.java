package logica.negocios;

import datos.entidades.Departamento;
import datos.entidades.Estado;
import datos.entidades.Reserva;
import datos.repositorios.IntervaloRepository;
import datos.repositorios.ReservaRepository;

import javax.annotation.Nullable;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@LocalBean
public class ReservaBean {
    @EJB
    private ReservaRepository reservaRepository;

    @EJB
    private IntervaloRepository intervaloRepository;

    public List<Reserva> listar(int offset, int limit, int ci) {
        return reservaRepository.listar(offset, limit, ci);
    }

    public Long listarCount(int ci) {
        return reservaRepository.listarCount(ci);
    }

    public void cancelar(int ci, int codigo) {
        Reserva reserva = reservaRepository.getByCiAndCodigo(ci, codigo);
        reserva.getEstado().visit(new Estado.Visitor<Void>() {
            @Override
            public Void pendiente() {
                if(reserva.getIntervalo().getAgenda().getEtapa().getVacuna().getCantDosis() > 1) {
                    reservaRepository.getPendientesByCiAndCodigoAgenda(ci, reserva.getIntervalo().getAgenda().getId())
                    .forEach(reservaCancelar -> {
                        reservaCancelar.setEstado(Estado.CANCELADA);
                        intervaloRepository.detach(reservaCancelar.getIntervalo());
                    });
                } else {
                    reserva.setEstado(Estado.CANCELADA);
                    intervaloRepository.detach(reserva.getIntervalo());
                }
                return null;
            }
        });
    }

    public Map<String, Integer> getDosisPorDepartamentos(String enfermedad, String vacuna, int etapa,
                                                         @Nullable LocalDate inicio, @Nullable LocalDate fin){
        if (inicio == null || fin == null){
            inicio = LocalDate.of(1999,1,1);
            fin = LocalDate.of(2800,1,1);
        }

        Map<String, Integer> dosisDepartamentos = new HashMap<>();
        for (Departamento dep : Departamento.values()){
            dosisDepartamentos.put(dep.name(),reservaRepository
                    .findCantidadDosisDadasDepartamento(dep, enfermedad, vacuna, etapa, inicio, fin).size());
        }

        return dosisDepartamentos;
    }


    public Integer findVacunadosHoy(String enfermedad, String vacuna, int etapa){
        return reservaRepository.findVacunadosHoy(enfermedad,  vacuna,  etapa);
    }
    public Integer findAgendadosProximos(String enfermedad, String vacuna, int etapa){
        return reservaRepository.findAgendadosProximos(enfermedad,  vacuna,  etapa);
    }

    public List<Reserva> findAllDosisDadas(String enfermedad, String vacuna, int etapa) {
        return reservaRepository.findAllDosisDadas(enfermedad, vacuna, etapa);
    }

    public List<Reserva> findAllDosisDadas(String enfermedad, String vacuna, int etapa, LocalDate comienzo, LocalDate fin) {
        return reservaRepository.findAllDosisDadas(enfermedad, vacuna, etapa, comienzo, fin);
    }
}
