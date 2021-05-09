package datos.entidades;

import java.time.LocalTime;
import java.util.List;

public class Turno {
	private LocalTime inicio, fin;
	private Vacunatorio vacunatorio;
	private List<Agenda> agendas;
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
	public Vacunatorio getVacunatorio() {
		return vacunatorio;
	}
	public void setVacunatorio(Vacunatorio vacunatorio) {
		this.vacunatorio = vacunatorio;
	}
	public List<Agenda> getAgendas() {
		return agendas;
	}
	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}
	public Turno(LocalTime inicio, LocalTime fin) {
		super();
		this.inicio = inicio;
		this.fin = fin;
	}
	public Turno() {
		super();
	}
	
	
}
