package br.com.sias.api.dto;

import br.com.sias.api.model.Agendamento;
import br.com.sias.api.model.enums.StatusAgendamento;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class AgendamentoResponse {
    private Long id;
    private String nomePaciente;
    private String nomeInstrutor;
    private LocalDateTime dataHora;
    private StatusAgendamento status;
    private String observacao;

    public AgendamentoResponse(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.nomePaciente = agendamento.getPaciente().getNome();
        this.nomeInstrutor = agendamento.getInstrutor().getNome();
        this.dataHora = agendamento.getDataHora();
        this.status = agendamento.getStatus();
        this.observacao = agendamento.getObservacao();
    }
}