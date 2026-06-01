package br.com.sias.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstatisticaEvolucaoDTO {

    private int    totalPacientesAvaliados;
    private int    pacientesComReducaoPeso;
    private int    pacientesComReducaoIMC;
    private double percentualSucessoPeso;

    private int    pacientesComReducaoCircAbdominal;
    private int    pacientesComPioraDepeso;
    private int    pacientesEstabilizados;
    private double mediaPerdaPesoKg;
    private double mediaReducaoImc;
    private double mediaReducaoCircAbdominal;
    private double percentualSucessoImc;
    private double percentualSucessoCircAbdominal;
}