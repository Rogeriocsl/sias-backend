package br.com.sias.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "unidade_basica_saude")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UnidadeBasicaSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da Unidade de Saúde é obrigatóro")
    @Size(min = 3, max = 150)
    @Column(nullable = false, length = 150)
    private String nomeUnidade;

    @NotBlank(message = "O endereço é obrigatório")
    @Column(nullable = false, length = 255)
    private String endereco;

    @NotBlank(message = "O Bairro é obrigatório")
    @Column(nullable = false, length = 100)
    private String bairro;

    @NotNull(message = "O número é obrigatório")
    @Column(nullable = false)
    private Integer numero;

    @NotBlank(message = "O nome do Enfermeiro Responsável é obrigatóro")
    @Size(min = 3, max = 150)
    @Column(nullable = false, length = 150)
    private String nomeEnfermeiroResponsavel;
}