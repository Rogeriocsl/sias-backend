package br.com.sias.api.model;

import br.com.sias.api.model.enums.StatusAgendamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "instrutor_id", nullable = false)
    private Usuario instrutor;

    @ManyToOne
    @JoinColumn(name = "instrutor_id", nullable = false)
    private Usuario instrutor;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;

    private String observacao;
}
