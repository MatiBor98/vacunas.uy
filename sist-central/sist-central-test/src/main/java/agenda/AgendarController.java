package agenda;

import datos.dtos.VacunatorioTieneAgendaDTO;
import datos.entidades.Departamento;
import datos.entidades.Intervalo;
import datos.entidades.Reserva;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.IntervaloServiceLocal;
import logica.servicios.local.ReservaServiceLocal;
import plataformainteroperabilidad.Trabajo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Path("/agendar")
@Stateless
public class AgendarController {

    @EJB
    private AgendaServiceLocal agendaServiceLocal;

    @EJB
    private ReservaServiceLocal reservaServiceLocal;

    @EJB
    private IntervaloServiceLocal intervaloServiceLocal;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @QueryParam("ci") int ci,
            @QueryParam("enfermedad") String enfermedad,
            @QueryParam("edad") int edad,
            @QueryParam("trabajo") Trabajo trabajo,
            @QueryParam("depto") Departamento depto,
            @QueryParam("fecha") String fechaStr
    ) {
        LocalDateTime intervaloHora = LocalDateTime.parse(fechaStr);
        WeekFields weekFields = WeekFields.of(new Locale("es", "UY"));

        LocalDate semana = LocalDate.of(intervaloHora.getYear(), intervaloHora.getMonth(), intervaloHora.getDayOfMonth())
                .with(weekFields.dayOfWeek(), 1).plusWeeks(1);
        List<VacunatorioTieneAgendaDTO> agendasParaCiudadanoPorDepartamento = agendaServiceLocal
                .findAgendasParaCiudadanoPorDepartamento(enfermedad, edad, trabajo, depto);

        Optional<List<LocalDateTime>> reservasFechas = agendasParaCiudadanoPorDepartamento.stream().findFirst().flatMap(
                va -> intervaloServiceLocal.getIntervalosByAgendaAndSemana(va.getAgenda().getId(), semana)
                        .stream().findAny())
                .map(intervalo -> reservaServiceLocal.efectuarReserva(
                        new Intervalo(intervaloHora, intervalo.getAgenda()), ci))
                .map(r -> r.stream().map(Reserva::getIntervalo)
                        .map(Intervalo::getFechayHora)
                        .collect(Collectors.toList()));

        return reservasFechas
                .map(r -> Response.ok().entity(r).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHorarios(
            @QueryParam("agendaId") int agendaId,
            @QueryParam("fecha") String fechaStr,
            @QueryParam("cant") int cant
    ) {
        AtomicReference<LocalDate> fechaIni = new AtomicReference<>(LocalDate.parse(fechaStr));
        List<LocalDateTime> fechas = new ArrayList<>(cant);
        while (fechas.size() < cant) {
            intervaloServiceLocal.getIntervalosByAgendaAndSemana(agendaId, fechaIni.get()).stream().forEach(intervalo -> {
                int capacidadPorTurno = intervalo.getAgenda()
                        .getHorarioPorDia()
                        .get(intervalo.getFechayHora().getDayOfWeek())
                        .getCapacidadPorTurno();
                int cantidadReservasPendientes = intervalo.getCantidadReservasPendientes();
                for (int i = 0; i < (capacidadPorTurno - cantidadReservasPendientes); i++) {
                    fechas.add(intervalo.getFechayHora());
                }
            });
            fechaIni.getAndUpdate(localDate -> localDate.plusWeeks(1));
        }

        return Response.ok().entity(fechas).build();
    }
}