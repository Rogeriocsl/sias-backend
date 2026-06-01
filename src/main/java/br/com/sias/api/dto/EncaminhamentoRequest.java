package br.com.sias.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EncaminhamentoRequest {
    private Long      pacienteId;
    private Long      turmaId;
    private String    status;
    private String    motivo;
    private LocalDate dataEncaminhamento;
    private String    observacoes;
}