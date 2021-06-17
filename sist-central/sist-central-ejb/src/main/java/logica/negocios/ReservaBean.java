package logica.negocios;

import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.CiudadanoServiceLocal;
import logica.servicios.local.LoteServiceLocal;
import logica.servicios.local.ReservaServiceLocal;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datos.dtos.CiudadanoDTO;
import datos.dtos.IntervaloDTO;
import datos.dtos.ReservaDTO;
import datos.entidades.Estado;
import datos.entidades.Reserva;
import datos.entidades.Vacunatorio;
import datos.repositorios.ReservaRepositoryLocal;

@Stateless
public class ReservaBean implements ReservaServiceLocal {
	
	@EJB
	ReservaRepositoryLocal reservaRepository;
	@EJB
	CiudadanoServiceLocal ciudadanoServiceLocal;
	@EJB
	AgendaServiceLocal agendaServiceLocal;
	@EJB
	LoteServiceLocal loteServiceLocal;

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
