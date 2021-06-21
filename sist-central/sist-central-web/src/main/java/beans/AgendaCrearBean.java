package beans;

import datos.dtos.AgendaDTO;
import datos.dtos.InformacionPosiblesIntervalosDTO;
import datos.entidades.Etapa;
import datos.entidades.Turno;
import datos.repositorios.EtapaRepositoryLocal;
import datos.repositorios.TurnoRepositoryLocal;
import logica.servicios.local.AgendaServiceLocal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named("agendaCrearBean")
@ViewScoped
public class AgendaCrearBean implements Serializable {
    @EJB
    private AgendaServiceLocal agendaService;

    @EJB
    private EtapaRepositoryLocal etapaRepositoryLocal;

    @EJB
    private TurnoRepositoryLocal turnoRepositoryLocal;

    private int cantidadPaginas;
    private int paginaActual;
    private List<Turno> turnos;

    private LocalDate inicio = LocalDate.now();
    private String nombre = "";
    private int turnoId = 0;
    private int etapaId = 0;
    private Map<DayOfWeek, Entrada> dayOfWeekHoraInicioFinDTOMap;
    private String error = null;

    @PostConstruct
    void postConstruct() {
        realizarBusqueda(0);
    }

    public AgendaCrearBean() {
        dayOfWeekHoraInicioFinDTOMap = Arrays.stream(DayOfWeek.values())
                .collect(Collectors.toMap(Function.identity(), (a) -> new Entrada()));
    }

    public List<Etapa> getEtapas() {
        return etapaRepositoryLocal.findVigentes();
    }

    public List<Turno> getTurnos() {
        return turnoId == 0 ?
                turnos :
                turnoRepositoryLocal.findById(turnoId)
                        .map(Collections::singletonList)
                        .orElse(turnos);
    }

    public List<Map.Entry<DayOfWeek, Entrada>> getDias() {
        return dayOfWeekHoraInicioFinDTOMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());
    }

    public String getDiaEnEsUy(DayOfWeek day) {
        return day.getDisplayName(TextStyle.FULL, new Locale("es", "UY"));
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public String getError() {
        return error;
    }

    public String guardarAgenda() {
        if(etapaId == 0) {
            error = "Debe selecionar una etapa";
            return null;
        }
        if(turnoId == 0) {
            error = "Debe selecionar un turno";
            return null;
        }

        try {
            var horarioPorDia = new HashMap<DayOfWeek, InformacionPosiblesIntervalosDTO>() {{
                dayOfWeekHoraInicioFinDTOMap.forEach((dayOfWeek, entrada) -> {
                    if (entrada.isHabilitado()) {
                        Integer capasidadPorTurno = entrada.getCapasidadPorTurno();
                        Integer minutosTurno = entrada.getMinutosTurno();
                        if(capasidadPorTurno == null) {
                            throw new RuntimeException("Capasidad Turno invalido para " + getDiaEnEsUy(dayOfWeek));
                        }
                        if(minutosTurno == null) {
                            throw new RuntimeException("Minutos en Turno invalido para " + getDiaEnEsUy(dayOfWeek));
                        }
                        put(dayOfWeek, new InformacionPosiblesIntervalosDTO(
                                entrada.getInicio(),
                                entrada.getFin(),
                                capasidadPorTurno,
                                minutosTurno));
                    }
                });
            }};
            agendaService.save(new AgendaDTO(0, horarioPorDia, turnoId, etapaId, inicio, null, nombre));
            return "Autoridad";
        } catch (EJBException ejbException)  {
            error = ejbException.getCause().getMessage();
            return null;
        } catch (Exception def) {
            error = def.getMessage();
            return null;
        }
    }

    public void realizarBusqueda(int pagina) {
        if (turnoId != 0) {
            return;
        }
        long cantidadTurnos = turnoRepositoryLocal.findCount();
        this.cantidadPaginas = (int) Math.ceil(cantidadTurnos / 5.0);
        if (pagina < cantidadPaginas && pagina >= 0) {
            paginaActual = pagina;
        }
        this.turnos = turnoRepositoryLocal.findPage(paginaActual * 5, 5);
    }

    public void anterior() {
        realizarBusqueda(paginaActual - 1);
    }

    public void siguiente() {
        realizarBusqueda(paginaActual + 1);
    }

    public int getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(int turnoId) {
        this.turnoId = turnoId;
    }

    public int getEtapaId() {
        return etapaId;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public int getCantidadPaginas() {
        return cantidadPaginas;
    }

    public void setEtapaId(int etapaId) {
        this.etapaId = etapaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static class Entrada {
        private boolean habilitado = false;
        private LocalTime inicio;
        private LocalTime fin;
        private Integer capasidadPorTurno;
        private Integer minutosTurno;

        public Entrada() {
        }

        public boolean isHabilitado() {
            return habilitado;
        }

        public void setHabilitado(boolean habilitado) {
            this.habilitado = habilitado;
        }

        public LocalTime getInicio() {
            return inicio;
        }

        public void setInicio(LocalTime inicio) {
            this.inicio = inicio;
        }

        public LocalTime getFin() {
            return fin;
        }

        public void setFin(LocalTime fin) {
            this.fin = fin;
        }

        public Integer getCapasidadPorTurno() {
            return capasidadPorTurno;
        }

        public void setCapasidadPorTurno(Integer capasidadPorTurno) {
            this.capasidadPorTurno = capasidadPorTurno;
        }

        public Integer getMinutosTurno() {
            return minutosTurno;
        }

        public void setMinutosTurno(Integer minutosTurno) {
            this.minutosTurno = minutosTurno;
        }
    }
}
