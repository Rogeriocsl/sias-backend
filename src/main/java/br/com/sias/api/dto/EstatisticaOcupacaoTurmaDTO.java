package br.com.sias.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstatisticaOcupacaoTurmaDTO {
    private Long   turmaId;
    private String turmaNome;
    private long   totalAlunos;
}