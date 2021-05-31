package beans;

import datos.dtos.VacunatorioTieneAgendaDTO;
import datos.entidades.*;
import datos.repositorios.ReservaRepository;
import io.jsonwebtoken.lang.Strings;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.EnfermedadServiceLocal;
import logica.servicios.local.EtapaController;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named("AgendarBean")
@SessionScoped
public class AgendarBean implements Serializable {
    @EJB
    private EnfermedadServiceLocal enfermedadController;

    @EJB
    private AgendaServiceLocal agendaServiceLocal;

    @EJB
    private EtapaController etapaController;

    @EJB
    private ReservaRepository reservaRepository;

    private Entrada entrada = new Entrada();

    private String enfermedaNombre = null;

    private boolean mostrarLista = false;

    private Boolean ciudadanoHabilitado = null;

    private Boolean yaTieneAgendaCiudadano = null;

    private List<Reserva> reservasRealizadas = Collections.emptyList();

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
        return entrada.vacunatorioAgneda != null ? Collections.singletonList(entrada.vacunatorioAgneda) :
                agendaServiceLocal.findAgendasParaCiudadanoPorDepartamento(entrada.enfermedad.getNombre(),
                        50, null, entrada.departamento);
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
        return reservasRealizadas;
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
        this.entrada.enfermedad = enfermedad;
        this.enfermedaNombre = null;
        this.ciudadanoHabilitado = etapaController
                .existeEtapaParaCiudadano(entrada.enfermedad.getNombre(), 50, null);
        this.yaTieneAgendaCiudadano = reservaRepository
                .existeReservaPendienteByCiudadanoEnfermedad(52050756, enfermedad.getNombre());
    }
    public void elegirVacunatorioAgneda(VacunatorioTieneAgendaDTO vacunatorioAgneda) {
        this.entrada.vacunatorioAgneda = vacunatorioAgneda;
        if(vacunatorioAgneda != null) {
            this.intevalosPorDia = agendaServiceLocal
                    .getIntervalos(entrada.vacunatorioAgneda.getAgenda().getId(), semana)
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
            reservasRealizadas = agendaServiceLocal.efectuarReserva(entrada.intervalo, 52050756);
            this.entrada = new Entrada();
        } catch(Exception e) {
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
        intevalosPorDia = agendaServiceLocal
                .getIntervalos(entrada.vacunatorioAgneda.getAgenda().getId(), semana)
                .stream().collect(Collectors.groupingBy(i->i.getFechayHora().getDayOfWeek()));
    }

    public String getDiaConFormatoUy(DayOfWeek dia) {
        return dia.getDisplayName(TextStyle.SHORT, Constantes.ES_UY);
    }

    public static class Entrada {
        private Enfermedad enfermedad = null;

        private Departamento departamento = null;

        private VacunatorioTieneAgendaDTO vacunatorioAgneda = null;

        private Intervalo intervalo = null;

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

