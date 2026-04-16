package br.com.sias.api.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class EncaminhamentoResponse {
    private Long id;
    private Long pacienteId;
    private LocalDate dataEncaminhamento;
    private String motivo;
    private String status;
    private String observacoes;
}
