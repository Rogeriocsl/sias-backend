package br.com.sias.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class TurmasRequest {
    @NotBlank(message = "O nome da Turma deve ser informado")
    @Size(min = 3, max = 150)
    private String nome;

    @NotBlank(message = "O nome do Educador é obrigatório")
    @Size(min = 3, max = 150)
    private String educador;
}