package br.com.sias.api.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class AvaliacaoFisicaResponse {
    private Long id;
    private Long pacienteId;
    private LocalDate dataAvaliacao;
    private Double peso;
    private Double altura;
    private Double imc;
    private String pressaoArterial;
    private Integer frequenciaCardiaca;
    private Double circunferenciaAbdominal;
    private String observacoes;
}