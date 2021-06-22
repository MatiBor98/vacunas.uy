package beans;

import datos.dtos.CiudadanoDTO;
import datos.dtos.VacunatorioTieneAgendaDTO;
import datos.entidades.Departamento;
import datos.entidades.Enfermedad;
import datos.entidades.Intervalo;
import datos.entidades.Reserva;
import datos.repositorios.ReservaRepository;
import io.jsonwebtoken.lang.Strings;
import logica.negocios.ReservaBean;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.EnfermedadServiceLocal;
import logica.servicios.local.EtapaController;
import logica.servicios.local.IntervaloServiceLocal;
import plataformainteroperabilidad.Ciudadano;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named("AgendarBean")
@ViewScoped
public class AgendarBean implements Serializable {
    @EJB
    private EnfermedadServiceLocal enfermedadController;

    @EJB
    private AgendaServiceLocal agendaServiceLocal;

    @EJB
    private IntervaloServiceLocal intervaloServiceLocal;

    @EJB
    private EtapaController etapaController;

    @EJB
    private ReservaRepository reservaRepository;

    @EJB
    private ReservaBean reservaService;

    @Inject
    private UsuarioLogueadoBean usuarioLogueadoBean;

    private Entrada entrada = new Entrada();

    private String enfermedaNombre = null;

    private boolean mostrarLista = false;

    private Boolean ciudadanoHabilitado = null;

    private Boolean yaTieneAgendaCiudadano = null;

    private LocalDate semana;

    private Map<DayOfWeek, List<Intervalo>> intevalosPorDia = null;

    private final LocalDate fechaMax;

    private final LocalDate fechaMin;

    private boolean procesando = false;

    public AgendarBean() {
        WeekFields weekFields = WeekFields.of(Constantes.ES_UY);
        this.semana = LocalDate.now()
                .with(weekFields.dayOfWeek(), 1);
        this.fechaMin = semana;
        this.fechaMax = semana.plusMonths(3);
    }

    public List<Enfermedad> getEnfermedades() {
        return !mostrarLista || !Strings.hasText(enfermedaNombre) ? Collections.emptyList() : enfermedadController
                .findPage(0, 10, enfermedaNombre);
    }

    public List<VacunatorioTieneAgendaDTO> getAgendas() {
        Ciudadano ciudadano = usuarioLogueadoBean.getCiudadanoPlataforma();
        LocalDate fechaNac = LocalDate.parse(ciudadano.getFechaNacimiento());
        int edad = Period.between(fechaNac, LocalDate.now()).getYears();
        return entrada.vacunatorioAgneda != null ? Collections.singletonList(entrada.vacunatorioAgneda) :
                agendaServiceLocal.findAgendasParaCiudadanoPorDepartamento(entrada.enfermedad.getNombre(),
                        edad, ciudadano.getTrabajadorEscencial(), entrada.departamento);
    }

    public List<DayOfWeek> getDiasIntervalos() {
        return intevalosPorDia.keySet().stream().sorted().collect(Collectors.toList());
    }

    public List<Intervalo> getIntervalosPorDia(DayOfWeek dia) {
        return intevalosPorDia.get(dia);
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public String getEnfermedaNombre() {
        return enfermedaNombre;
    }

    public void setEnfermedaNombre(String enfermedaNombre) {
        this.enfermedaNombre = enfermedaNombre;
    }

    public Boolean getCiudadanoHabilitado() {
        return ciudadanoHabilitado;
    }

    public void setCiudadanoHabilitado(Boolean ciudadanoHabilitado) {
        this.ciudadanoHabilitado = ciudadanoHabilitado;
    }

    public Boolean getYaTieneReservaCiudadano() {
        return yaTieneAgendaCiudadano;
    }

    public void setYaTieneReservaCiudadano(Boolean yaTieneReservaCiudadano) {
        this.yaTieneAgendaCiudadano = yaTieneReservaCiudadano;
    }

    public List<Reserva> getReservasRealizadas() {
        return entrada.reservasRealizadas;
    }

    public LocalDate getSemana() {
        return semana;
    }

    public void setSemana(LocalDate semana) {
        this.semana = semana;
    }

    public String getFechaMax() {
        return fechaMax.format(DateTimeFormatter.ISO_WEEK_DATE).substring(0, 8);
    }

    public String getFechaMin() {
        return fechaMin.format(DateTimeFormatter.ISO_WEEK_DATE).substring(0, 8);
    }

    public void mostrarLista(boolean mostrar) {
        this.mostrarLista = mostrar;
    }

    public void elegirEnfermedad(Enfermedad enfermedad) {
        Ciudadano ciudadano = usuarioLogueadoBean.getCiudadanoPlataforma();
        CiudadanoDTO ciudadanoDTO = usuarioLogueadoBean.getCiudadano();
        LocalDate fechaNac = LocalDate.parse(ciudadano.getFechaNacimiento());
        int edad = Period.between(fechaNac, LocalDate.now()).getYears();

        this.entrada.enfermedad = enfermedad;
        this.enfermedaNombre = null;
        this.ciudadanoHabilitado = etapaController
                .existeEtapaParaCiudadano(entrada.enfermedad.getNombre(), edad, ciudadano.getTrabajadorEscencial());
        this.yaTieneAgendaCiudadano = reservaRepository
                .existeReservaPendienteByCiudadanoEnfermedad(ciudadanoDTO.getCi(), enfermedad.getNombre());
    }

    public void elegirVacunatorioAgneda(VacunatorioTieneAgendaDTO vacunatorioAgneda) {
        this.entrada.vacunatorioAgneda = vacunatorioAgneda;
        if (vacunatorioAgneda != null) {
            this.intevalosPorDia = intervaloServiceLocal
                    .getIntervalosByAgendaAndSemana(entrada.vacunatorioAgneda.getAgenda().getId(), semana)
                    .stream().collect(Collectors.groupingBy(i -> i.getFechayHora().getDayOfWeek()));
        } else {
            this.intevalosPorDia = null;
        }
    }

    public boolean isProcesando() {
        return procesando;
    }

    public void elegirIntervalo(Intervalo intervalo) {
        procesando = true;
        entrada.intervalo = intervalo;
    }

    public void concretarAgenda() {
        try {
            CiudadanoDTO ciudadano = usuarioLogueadoBean.getCiudadano();
            Intervalo intervalo = entrada.intervalo;
            limpiarEntrada();
            this.entrada.reservasRealizadas = reservaService.efectuarReserva(intervalo, ciudadano.getCi());
        } catch (Exception e) {
            actualizarIntervalos();
            System.out.println("No se puedo realizar la reserva!");
        } finally {
            procesando = false;
        }
    }

    public void limpiarEntrada() {
        this.entrada = new Entrada();
    }

    public void actualizarIntervalos() {
        intevalosPorDia = intervaloServiceLocal
                .getIntervalosByAgendaAndSemana(entrada.vacunatorioAgneda.getAgenda().getId(), semana)
                .stream().collect(Collectors.groupingBy(i -> i.getFechayHora().getDayOfWeek()));
    }

    public String getDiaConFormatoUy(DayOfWeek dia) {
        return dia.getDisplayName(TextStyle.SHORT, Constantes.ES_UY);
    }

    public static class Entrada {
        private Enfermedad enfermedad = null;

        private Departamento departamento = null;

        private VacunatorioTieneAgendaDTO vacunatorioAgneda = null;

        private Intervalo intervalo = null;

        private List<Reserva> reservasRealizadas = Collections.emptyList();

        public Enfermedad getEnfermedad() {
            return enfermedad;
        }

        public Departamento getDepartamento() {
            return departamento;
        }

        public void setDepartamento(Departamento departamento) {
            this.departamento = departamento;
        }

        public VacunatorioTieneAgendaDTO getVacunatorioAgneda() {
            return vacunatorioAgneda;
        }

        public Intervalo getIntervalo() {
            return intervalo;
        }
    }
}

