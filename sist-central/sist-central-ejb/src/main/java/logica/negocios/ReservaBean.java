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
public class ReservaBean implements ReservaServiceLocal{
    @EJB
    private ReservaRepository reservaRepository;

	@EJB
	private CiudadanoServiceLocal ciudadanoServiceLocal;
	
	@EJB
	private AgendaServiceLocal agendaServiceLocal;
	
	@EJB
	private LoteServiceLocal loteServiceLocal;

    @Override
    public Map<String, Integer> getDosisPorDepartamentos( String enfermedad, String vacuna, int etapa) {
        Map<String, Integer> dosisDepartamentos = new HashMap<>();
        for (Departamento dep : Departamento.values()){
            dosisDepartamentos.put(dep.name(),reservaRepository
                    .findCantidadDosisDadasDepartamento(dep, enfermedad, vacuna, etapa).size());
        }

        return dosisDepartamentos;
    }

    @Override
    public Integer findVacunadosHoy(String enfermedad, String vacuna, int etapa) {
        return reservaRepository.findVacunadosHoy(enfermedad,  vacuna,  etapa);
    }

    @Override
    public Integer findAgendadosProximos(String enfermedad, String vacuna, int etapa) {
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
