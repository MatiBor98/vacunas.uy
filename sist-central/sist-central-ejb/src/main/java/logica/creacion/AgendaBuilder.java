package logica.creacion;

import datos.entidades.Agenda;
import datos.entidades.Etapa;
import datos.entidades.InformacionPosiblesIntervalos;
import datos.entidades.Turno;
import io.jsonwebtoken.lang.Strings;

import java.time.*;
import java.util.Map;

public class AgendaBuilder {
    private int id;
    private String nombre;
    private LocalDate inicio;
    private LocalDate fin;
    private Etapa etapa;
    private Turno turno;
    private Map<DayOfWeek, InformacionPosiblesIntervalos> horarioPorDia;

    public AgendaBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public AgendaBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public AgendaBuilder setInicio(LocalDate inicio) {
        this.inicio = inicio;
        return this;
    }

    public AgendaBuilder setFin(LocalDate fin) {
        this.fin = fin;
        return this;
    }

    public AgendaBuilder setEtapa(Etapa etapa) {
        this.etapa = etapa;
        return this;
    }

    public AgendaBuilder setHorarioPorDia(Map<DayOfWeek, InformacionPosiblesIntervalos> horarioPorDia) {
        this.horarioPorDia = horarioPorDia;
        return this;
    }

    public AgendaBuilder setTurno(Turno turno) {
        this.turno = turno;
        return this;
    }

    public Agenda createAgenda() {
        validar();
        return new Agenda(id, nombre, inicio, fin, etapa, horarioPorDia, turno);
    }

    private void validar() {
        LocalTime inicioTurno = turno.getInicio();
        LocalTime finTurno = turno.getFin();

        if(!Strings.hasText(nombre)) {
            throw new RuntimeException("El nombre es obligatorio");
        }
        if(inicio == null) {
            throw new RuntimeException("Fecha de inicio es obligatoria");
        }
        if(horarioPorDia.size() == 0) {
            throw new RuntimeException("No se puede crear una agenda sin horarios");
        }
        if(!horarioPorDia.values().stream().allMatch(i -> i.getInicio() != null && i.getFin() != null)) {
            throw new RuntimeException("Debe completar toda la informacion para los dias habilitados");
        }
        if(!horarioPorDia.values().stream().allMatch(i -> i.getInicio().isBefore(i.getFin()))) {
            throw new RuntimeException("La fecha de inicio debe ser menor que la fcha de fin para todos los dias");
        }
        if(!horarioPorDia.values().stream().allMatch(c -> c.getCapacidadPorTurno() > 0)) {
            throw new RuntimeException("La capasidad debe ser un numero positivo");
        }
        if(!horarioPorDia.values().stream().allMatch(c -> c.getMinutosTurno() > 0)) {
            throw new RuntimeException("Los minutos en el turno debe ser un numero positivo");
        }
        if (!horarioPorDia.values().stream()
                .allMatch(i -> (Duration.between(i.getInicio(), i.getFin()).toMinutes() % i.getMinutosTurno()) == 0)) {
            throw new RuntimeException("La duracion del turno debe coincidir con el inicio y fin");
        }
        if (!horarioPorDia.values().stream().map(InformacionPosiblesIntervalos::getInicio)
                .map(inicioTurno::compareTo).allMatch(res -> res <= 0)) {
            throw new RuntimeException("La hora de inicio de la agenda debe ser posterior al del turno para todos los dias");
        }
        if (!horarioPorDia.values().stream().map(InformacionPosiblesIntervalos::getFin).map(finTurno::compareTo)
                .allMatch(res -> res >= 0)) {
            throw new RuntimeException("La hora de inicio de la agenda debe ser posterior al del turno para todos los dias");
        }
    }
}