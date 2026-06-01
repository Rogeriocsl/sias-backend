package br.com.sias.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstatisticaUbsDTO {
    private String nomeUbs;
    private Long quantidade;
}