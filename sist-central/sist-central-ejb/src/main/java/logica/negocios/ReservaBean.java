package logica.negocios;

import datos.entidades.Estado;
import datos.entidades.Reserva;
import datos.repositorios.IntervaloRepository;
import datos.repositorios.ReservaRepository;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;

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
}
