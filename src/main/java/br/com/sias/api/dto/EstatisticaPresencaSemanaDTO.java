package br.com.sias.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstatisticaPresencaSemanaDTO {
    private String semana;
    private long   presentes;
    private long   faltas;
    private long   justificadas;
}