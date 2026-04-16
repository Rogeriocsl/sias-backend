package br.com.sias.api.model;

import br.com.sias.api.model.enums.EncaminhamentoStatus;
import br.com.sias.api.model.enums.EncaminhamentoMotivo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Encaminhamento de ter um paciente")
    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "data_encaminhamento", nullable = false)
    private LocalDate dataEncaminhamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo", length = 50)
    private EncaminhamentoMotivo motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private EncaminhamentoStatus status;

    @Lob
    @Column(name = "observacoes")
    private String observacoes;

}
