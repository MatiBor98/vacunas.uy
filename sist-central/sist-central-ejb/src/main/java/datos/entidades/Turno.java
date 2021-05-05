package datos.entidades;

import java.time.LocalTime;
import java.util.List;

public class Turno {
	private LocalTime inicio, fin;
	private Vacunatorio vacunatorio;
	private List<Agenda> agendas;
}
