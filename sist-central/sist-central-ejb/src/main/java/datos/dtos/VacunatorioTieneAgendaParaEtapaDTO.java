package datos.dtos;

public class VacunatorioTieneAgendaParaEtapaDTO {

	private final VacunatorioDTO vacunatorio;
	private final AgendaDTO agenda;
	private final EtapaDTO etapa;

	public VacunatorioTieneAgendaParaEtapaDTO(VacunatorioDTO vacunatorio, AgendaDTO agenda, EtapaDTO etapa) {
		this.vacunatorio = vacunatorio;
		this.agenda = agenda;
		this.etapa = etapa;
	}

	public VacunatorioDTO getVacunatorio() {
		return vacunatorio;
	}

	public AgendaDTO getAgenda() {
		return agenda;
	}

	public EtapaDTO getEtapa() {
		return etapa;
	}
}
