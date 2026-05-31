package br.com.sias.api.model;

import br.com.sias.api.model.enums.DoencaCronica;
import br.com.sias.api.model.enums.Genero;
import br.com.sias.api.model.enums.TipoSanguineo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11)
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "O telefone é obrigatório")
    @Column(nullable = false, length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_sanguineo", length = 10)
    private TipoSanguineo tipoSanguineo;

    @ElementCollection(targetClass = DoencaCronica.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "paciente_condicoes", joinColumns = @JoinColumn(name = "paciente_id"))
    @Column(name = "condicao")
    private List<DoencaCronica> condicoesSaude;

    @NotNull(message = "O paciente deve estar vinculado a uma Unidadade Básica de Saúde")
    @ManyToOne(optional = false)
    @JoinColumn(name = "unidade_origem_id", nullable = false)
    private UnidadeBasicaSaude unidadeOrigem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = true)
    private Turmas turmas;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Encaminhamento> encaminhamentos;
}
