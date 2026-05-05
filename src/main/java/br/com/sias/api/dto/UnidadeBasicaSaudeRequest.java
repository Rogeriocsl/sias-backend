package br.com.sias.api.dto;

import lombok.*;

@Getter
@Setter
public class UnidadeBasicaSaudeRequest {
    private String nomeUnidade;
    private String endereco;
    private String bairro;
    private Integer numero;
    private String nomeEnfermeiroResponsavel;
}