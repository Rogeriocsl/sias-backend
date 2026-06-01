package br.com.sias.api.dto;

import br.com.sias.api.model.enums.StatusAgendamento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class AgendamentoRequest {
    @NotNull(message = "O paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "O instrutor é obrigatório")
    private Long instrutorId;

    @NotNull(message = "A data e hora são obrigatórias")
    private LocalDateTime dataHora;

    private String observacao;

    private StatusAgendamento status;
}