package br.com.sias.api.model;

import br.com.sias.api.model.enums.EncaminhamentoMotivo;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "encaminhamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Encaminhamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = true)
    private Turmas turma;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EncaminhamentoMotivo motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EncaminhamentoStatus status;

    @Column(name = "data_encaminhamento")
    private LocalDate dataEncaminhamento;

    @Column(length = 500)
    private String observacoes;
}