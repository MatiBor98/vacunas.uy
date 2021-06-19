package logica.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TimerTask;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import datos.dtos.AgendaDTO2;
import datos.dtos.AsignacionDTO;
import datos.dtos.EtapaDTO2;
import datos.dtos.LoteDTO;
import datos.dtos.PlanVacunacionDTO2;
import datos.dtos.PuestoVacunacionDTO;
import datos.dtos.ReservaDTO;
import datos.dtos.TurnoDTO;
import datos.dtos.VacunatorioDTO;
import datos.entidades.*;
import datos.exceptions.CiudadanoRegistradoException;
import datos.exceptions.PuestoVacunacionNoExistenteException;
import datos.exceptions.VacunatorioNoExistenteException;
import datos.repositorios.AgendaRepositoryLocal;
import datos.repositorios.AgendaRepositoryRemote;
import datos.repositorios.CiudadanoRepositoryLocal;
import datos.repositorios.CiudadanoRepositoryRemote;
import datos.repositorios.EtapaRepositoryLocal;
import datos.repositorios.EtapaRepositoryRemote;
import datos.repositorios.IntervaloRepositoryLocal;
import datos.repositorios.IntervaloRepositoryRemote;
import datos.repositorios.LoteRepositoryLocal;
import datos.repositorios.LoteRepositoryRemote;
import datos.repositorios.PlanVacunacionRepositoryLocal;
import datos.repositorios.PlanVacunacionRepositoryRemote;
import datos.repositorios.PuestoVacunacionRepositoryLocal;
import datos.repositorios.PuestoVacunacionRepositoryRemote;
import datos.repositorios.ReservaRepositoryLocal;
import datos.repositorios.ReservaRepositoryRemote;
import datos.repositorios.TurnoRepositoryLocal;
import datos.repositorios.TurnoRepositoryRemote;
import datos.repositorios.VacunaRepositoryLocal;
import datos.repositorios.VacunaRepositoryRemote;
import datos.repositorios.VacunatorioRepositoryLocal;
import datos.repositorios.VacunatorioRepositoryRemote;
import logica.schedule.DatosVacunatorio;
import logica.schedule.DatosVacunatorioSchedule;
import logica.servicios.local.VacunatorioControllerLocal;



@Stateless
public class ActualizadorBean extends TimerTask {
	
	@EJB
	private VacunatorioControllerLocal vControllerLocal;

	@Override
	public void run() {		
		vControllerLocal.actualizar();
	}

	

}
