package br.com.sias.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EncaminhamentoResponse {
    private Long      id;

    private Long      pacienteId;
    private String    pacienteNome;
    private String    pacienteCpf;

    private Long      turmaId;
    private String    turmaNome;

    private String    motivo;
    private String    status;
    private LocalDate dataEncaminhamento;
    private String    observacoes;
}