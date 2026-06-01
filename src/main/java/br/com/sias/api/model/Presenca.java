package br.com.sias.api.model;

import br.com.sias.api.model.enums.AtividadeFisica;
import br.com.sias.api.model.enums.StatusPresenca;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*; // Importa todas as anotações do Lombok
import java.time.LocalDate;

@Entity
@Table(name = "presencas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Presenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "data_presenca")
    private LocalDate dataPresenca;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPresenca status;

    @Lob
    @Column(name = "observacao")
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "atividade")
    private AtividadeFisica atividade;
}