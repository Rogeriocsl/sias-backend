package br.com.sias.api.dto;

import lombok.*;

@Getter
@Setter
public class UnidadeBasicaSaudeResponse {
    private Long id;
    private String nomeUnidade;
    private String endereco;
    private String bairro;
    private Integer numero;
    private String nomeEnfermeiroResponsavel;
}