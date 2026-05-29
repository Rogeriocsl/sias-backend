package br.com.sias.api.dto;

import lombok.*;

@Getter
@Setter
public class TurmasResponse {
    private Long id;
    private String nome;
    private String educador;
    private Integer quantidadeAlunos;
}