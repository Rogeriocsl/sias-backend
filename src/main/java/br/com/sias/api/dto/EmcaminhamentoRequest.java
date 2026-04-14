package br.com.sias.api.dto;

import java.time.LocalDate;

public class EmcaminhamentoRequest {
    private Long pacienteId;
    private LocalDate DataEncaminhamento;
    private String motivo;
    private String status;
    private String observacoes;
}
