package logica.negocios;

import datos.entidades.*;
import logica.servicios.remoto.ReservaServiceRemote;
import repositorios.CiudadanoRepository;
import repositorios.IntervaloRepository;
import repositorios.ReservaRepository;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ReservaBean implements ReservaServiceRemote {
    @EJB
    private ReservaRepository reservaRepository;

    @EJB
    private IntervaloRepository intervaloRepository;

    @EJB
    private CiudadanoRepository ciudadanoRepository;

    @Override
    public List<Reserva> listar(int offset, int limit, int ci) {
        return reservaRepository.listar(offset, limit, ci);
    }

    @Override
    public Long listarCount(int ci) {
        return reservaRepository.listarCount(ci);
    }

    @Override
    public List<Reserva> efectuarReserva(Intervalo intervalo, int ciudadanoCi) {
        Ciudadano ciudadano = ciudadanoRepository.find(ciudadanoCi).orElseThrow(RuntimeException::new);
        Vacuna vacuna = intervalo.getAgenda().getEtapa().getVacuna();
        List<Reserva> reservasHechas = new ArrayList<>(vacuna.getCantDosis());

        Intervalo intervaloCreado = intervaloRepository.findOrCreate(intervalo);
        Reserva reserva = new Reserva(Estado.PENDIENTE, ciudadano, intervaloCreado, 1);
        intervaloCreado.addReserva(reserva);
        reservaRepository.save(reserva);
        reservasHechas.add(reserva);

        for (int i = 1; i < vacuna.getCantDosis(); i++) {
            LocalDateTime fechaSiguienteDosis = intervalo.getFechayHora()
                    .plusDays((long) vacuna.getDosisSeparacionDiasMultiploSemana() * i);
            Intervalo intervaloCreadoSiguienteDosis = intervaloRepository.findOrCreate(
                    new Intervalo(fechaSiguienteDosis, intervalo.getAgenda()));
            Reserva reservaSigueinteDosis = new Reserva(Estado.PENDIENTE, ciudadano, intervaloCreadoSiguienteDosis,
                    i + 1);
            intervaloCreadoSiguienteDosis.addReserva(reservaSigueinteDosis);
            reservaRepository.save(reservaSigueinteDosis);
            reservasHechas.add(reservaSigueinteDosis);
        }

        return reservasHechas;
    }

    @Override
    public void cancelar(int ci, int codigo) {
        Reserva reserva = reservaRepository.getByCiAndCodigo(ci, codigo);
        reserva.getEstado().visit(new Estado.Visitor<Void>() {
            @Override
            public Void pendiente() {
                if (reserva.getIntervalo().getAgenda().getEtapa().getVacuna().getCantDosis() > 1) {
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

    public boolean existeReservaPendienteByCiudadanoEnfermedad(int ci, String enfermedad) {
        return reservaRepository.existeReservaPendienteByCiudadanoEnfermedad(ci, enfermedad);
    }
}
