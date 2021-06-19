package logica.negocios;

import datos.DistributedLock;
import datos.dtos.CiudadanoDTO;
import datos.dtos.IntervaloDTO;
import datos.dtos.IntervaloDTO2;
import datos.dtos.ReservaDTO;
import datos.entidades.*;
import datos.repositorios.CiudadanoRepositoryLocal;
import datos.repositorios.IntervaloRepository;
import datos.repositorios.ReservaRepository;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.CiudadanoServiceLocal;
import logica.servicios.local.LoteServiceLocal;
import logica.servicios.local.ReservaServiceLocal;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@LocalBean
public class ReservaBean implements ReservaServiceLocal {
    @EJB
    private CiudadanoRepositoryLocal ciudadanoRepository;

    @EJB
    private ReservaRepository reservaRepository;

    @EJB
    private IntervaloRepository intervaloRepository;
    
	@EJB
	private CiudadanoServiceLocal ciudadanoServiceLocal;
	
	@EJB
	private AgendaServiceLocal agendaServiceLocal;
	
	@EJB
	private LoteServiceLocal loteServiceLocal;

	@EJB
    private DistributedLock distributedLock;

    public List<Reserva> listar(int offset, int limit, int ci) {
        return reservaRepository.listar(offset, limit, ci);
    }

    public Long listarCount(int ci) {
        return reservaRepository.listarCount(ci);
    }

    public void cancelar(int ci, int codigo) {
        Reserva reserva = reservaRepository.getByCiAndCodigo(ci, codigo);
        efectuarCancelacion(reserva);
    }

    public List<Reserva> efectuarReserva(Intervalo intervalo, int ciudadanoCi) {
        Ciudadano ciudadano = ciudadanoRepository.find(ciudadanoCi).orElseThrow(RuntimeException::new);
        Vacuna vacuna = intervalo.getAgenda().getEtapa().getVacuna();
        List<Reserva> reservasHechas = new ArrayList<>(vacuna.getCantDosis());

        //bloqueo el accesso solo para los que reservan el mismo intervalo
        distributedLock.blockingLock(intervalo.getLockId());
        
        Intervalo intervaloCreado = intervaloRepository.findOrCreate(intervalo);
        Reserva reserva = new Reserva(Estado.PENDIENTE, ciudadano, intervaloCreado, 1);
        intervaloCreado.addReserva(reserva);
        reservaRepository.save(reserva);
        reservasHechas.add(reserva);

        for(int i = 1; i < vacuna.getCantDosis(); i++) {
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

    public Map<String, Integer> getDosisPorDepartamentos( String enfermedad, String vacuna, int etapa){
        Map<String, Integer> dosisDepartamentos = new HashMap<>();
        for (Departamento dep : Departamento.values()){
            dosisDepartamentos.put(dep.name(),reservaRepository
                    .findCantidadDosisDadasDepartamento(dep, enfermedad, vacuna, etapa).size());
        }

        return dosisDepartamentos;
    }

    public Integer findVacunadosHoy(String enfermedad, String vacuna, int etapa){
        return reservaRepository.findVacunadosHoy(enfermedad,  vacuna,  etapa);
    }
    public Integer findAgendadosProximos(String enfermedad, String vacuna, int etapa){
        return reservaRepository.findAgendadosProximos(enfermedad,  vacuna,  etapa);
    }
    
	@Override
	public List<ReservaDTO> getReservasDTO(Vacunatorio vac) {
		List<Reserva> reservas = reservaRepository.findReservasVacunatorio(vac);
		List<ReservaDTO> res = new ArrayList<>();
		for(Reserva reserva:reservas) {
			ReservaDTO resDTO = new ReservaDTO();
			resDTO.setCodigo(reserva.getCodigo());
			resDTO.setEstado(reserva.getEstado());
			resDTO.setParaDosis(reserva.getParaDosis());
			resDTO.setLote(reserva.getLote());
			CiudadanoDTO ciudDTO = ciudadanoServiceLocal.getCiudadanoDTO(reserva.getCiudadano());
			IntervaloDTO2 intDTO =agendaServiceLocal.getIntervaloDTO(reserva.getIntervalo());
			resDTO.setCiudadano(ciudDTO);
			resDTO.setIntervalo(intDTO);
			res.add(resDTO);
		}
		return res;
	}

	@Override
	public void confirmarVacuna(int idReserva, String idLote) {
		Reserva res = reservaRepository.findByID(idReserva);
		if(res != null) {
			res.setEstado(Estado.VACUNADO);
			res.setLote(idLote);
			loteServiceLocal.decrementar(Integer.valueOf(idLote));
		}
		
	}

	@Override
	public void cancelarVacuna(int codigo) {
		Reserva res = reservaRepository.findByID(codigo);
		if(res != null) {
            efectuarCancelacion(res);
		}
	}

	private void efectuarCancelacion(Reserva reserva) {
        reserva.getEstado().visit(new Estado.Visitor<Void>() {
            @Override
            public Void pendiente() {
                if(reserva.getIntervalo().getAgenda().getEtapa().getVacuna().getCantDosis() > 1) {
                    reservaRepository.getPendientesByCiAndCodigoAgenda(reserva.getCiudadano().getCi(),
                            reserva.getIntervalo().getAgenda().getId())
                            .forEach(reservaCancelar -> reservaCancelar.setEstado(Estado.CANCELADA));
                } else {
                    reserva.setEstado(Estado.CANCELADA);
                }
                return null;
            }
        });
    }

}
