package br.com.sias.api.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class EncaminhamentoRequest {
    private Long pacienteId;
    private LocalDate dataEncaminhamento;
    private String motivo;
    private String observacoes;
}
