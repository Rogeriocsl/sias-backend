package br.com.sias.api.dto;

import java.time.LocalDate;

public class EmcaminhamentoResponse {
    private Long pacienteId;
    private LocalDate DataEncaminhamento;
    private String motivo;
    private String status;
    private String observacoes;
}
