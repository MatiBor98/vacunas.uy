package datos.dtos;

public class VacunatorioTieneAgendaDTO {

	private final VacunatorioDTO vacunatorio;
	private final AgendaDTO agenda;

	public VacunatorioTieneAgendaDTO(VacunatorioDTO vacunatorio, AgendaDTO agenda) {
		this.vacunatorio = vacunatorio;
		this.agenda = agenda;
	}

	public VacunatorioDTO getVacunatorio() {
		return vacunatorio;
	}

	public AgendaDTO getAgenda() {
		return agenda;
	}
}
