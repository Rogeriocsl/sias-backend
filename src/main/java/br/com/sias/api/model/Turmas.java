package br.com.sias.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "turmas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Turmas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da Turma deve ser informado")
    @Size(min = 3, max = 150, message = "O nome deve ter entre 3 à 150 caractéres")
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank(message = "O nome do Educador é obrigatóro")
    @Size(min = 3, max = 150, message = "O nome do Educador deve ter entre 3 à 150 caractéres")
    @Column(nullable = false, length = 150)
    private String educador;
}