package br.com.sias.api.dto;

import lombok.*;

@Getter
@Setter
public class TurmasRequest {
    private String nome;
    private String educador;
    private Integer quantidadeAlunos;
}