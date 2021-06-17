package logica.negocios;

import datos.dtos.CiudadanoDTO;
import datos.dtos.IntervaloDTO;
import datos.dtos.ReservaDTO;
import datos.entidades.Departamento;
import datos.entidades.Estado;
import datos.entidades.Reserva;
import datos.entidades.Vacunatorio;
import datos.repositorios.IntervaloRepository;
import datos.repositorios.ReservaRepository;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.CiudadanoServiceLocal;
import logica.servicios.local.LoteServiceLocal;
import logica.servicios.local.ReservaServiceLocal;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@LocalBean
public class ReservaBean implements ReservaServiceLocal{
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
			IntervaloDTO intDTO = agendaServiceLocal.getIntervaloDTO(reserva.getIntervalo());
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
			res.setEstado(Estado.CANCELADA);
		}
		
	}

}
