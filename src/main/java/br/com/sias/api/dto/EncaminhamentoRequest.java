package br.com.sias.api.dto;

import br.com.sias.api.model.enums.EncaminhamentoStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class EncaminhamentoRequest {
    private Long pacienteId;
    private LocalDate dataEncaminhamento;
    private String motivo;
    private EncaminhamentoStatus status;
    private String observacoes;
}
