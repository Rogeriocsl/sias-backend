package br.com.sias.api.dto;

import lombok.*;

@Getter
@Setter
public class AvaliacaoFisicaRequest {
    private Long pacienteId;
    private Double peso;
    private Double altura;
    private String pressaoArterial;
    private Integer frequenciaCardiaca;
    private Double circunferenciaAbdominal;
    private String observacoes;
}