package beans;

import datos.dtos.AgendaDTO;
import datos.entidades.Departamento;
import datos.entidades.Enfermedad;
import datos.entidades.Intervalo;
import datos.entidades.Vacunatorio;
import logica.servicios.local.AgendaServiceLocal;
import logica.servicios.local.EnfermedadController;
import logica.servicios.local.EtapaController;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Named("AgendarBean")
@SessionScoped
public class AgendarBean implements Serializable {
    @EJB
    private EnfermedadController enfermedadController;

    @EJB
    private AgendaServiceLocal agendaServiceLocal;

    @EJB
    private EtapaController etapaController;

    private Entrada entrada = new Entrada();

    private String enfermedaNombre = null;

    private Boolean ciudadanoHabilitado = null;

    private Supplier<LocalDate> nextDateSupplier = getNextDateSupplier();

    public List<Enfermedad> getEnfermedades() {
        return enfermedaNombre == null ? Collections.emptyList() : enfermedadController
                .findPage(0, 10, enfermedaNombre);
    }

    public List<VacunatorioAgneda> getAgendas() {
        //TODO: arreglar eso ya que se optiene de una forma en el servicio se papea y se dehace el mapeo
        // - se hizo asi solo para agilizar
        return entrada.vacunatorioAgneda != null ? Collections.singletonList(entrada.vacunatorioAgneda) :
                agendaServiceLocal.findAgendasParaCiudadanoPorDepartamento(entrada.enfermedad.getNombre(),
                        50, null, entrada.departamento)
                        .entrySet()
                        .stream()
                        .flatMap(e-> e.getValue().stream().map(a-> VacunatorioAgneda.of(e.getKey(), a)))
                        .collect(Collectors.toList());
    }

    public List<Intervalo> getIntervalos() {
        return agendaServiceLocal.getIntervalos(entrada.vacunatorioAgneda.agendaDTO.getId(), nextDateSupplier.get());
    }

    private Supplier<LocalDate> getNextDateSupplier() {
        AtomicReference<LocalDate> localDate = new AtomicReference<>(LocalDate.now());
        return () -> localDate.getAndSet(localDate.get().plusWeeks(1));
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

    public void onBlur() {
        this.enfermedaNombre = null;
    }

    public void elegirEnfermedad(Enfermedad enfermedad) {
        this.entrada.enfermedad = enfermedad;
        this.enfermedaNombre = null;
        this.ciudadanoHabilitado = etapaController
                .habilidadoCiudadano(entrada.enfermedad.getNombre(), 50, null);
    }

    public void elegirVacunatorioAgneda(VacunatorioAgneda vacunatorioAgneda) {
        this.entrada.vacunatorioAgneda = vacunatorioAgneda;
    }

    public void limpiarEntrada() {
        this.entrada = new Entrada();
    }

    public static class VacunatorioAgneda {
        private final Vacunatorio vacunatorio;
        private final AgendaDTO agendaDTO;

        public static VacunatorioAgneda of(Vacunatorio v, AgendaDTO a) {
            return new VacunatorioAgneda(v, a);
        }

        private VacunatorioAgneda(Vacunatorio vacunatorio, AgendaDTO agendaDTO) {
            this.vacunatorio = vacunatorio;
            this.agendaDTO = agendaDTO;
        }

        public Vacunatorio getVacunatorio() {
            return vacunatorio;
        }

        public AgendaDTO getAgendaDTO() {
            return agendaDTO;
        }
    }

    public static class Entrada {
        private Enfermedad enfermedad = null;

        private Departamento departamento = null;

        private VacunatorioAgneda vacunatorioAgneda = null;

        public Enfermedad getEnfermedad() {
            return enfermedad;
        }

        public Departamento getDepartamento() {
            return departamento;
        }

        public void setDepartamento(Departamento departamento) {
            this.departamento = departamento;
        }

        public VacunatorioAgneda getVacunatorioAgneda() {
            return vacunatorioAgneda;
        }
    }
}

