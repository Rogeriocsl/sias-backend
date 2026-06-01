package br.com.sias.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "avaliacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties

public class AvaliacaoFisica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "As avaliações devem estar vinculadas a um paciente")
    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "data_avaliacao", nullable = false)
    private LocalDate dataAvaliacao;

    @NotNull(message = "O peso é obrigatório")
    @Column(name = "peso", nullable = false)
    private Double peso;

    @NotNull(message = "A altura é obrigatória")
    @Column(name = "altura", nullable = false)
    private Double altura;

    @Column(name = "imc")
    private Double imc;

    @Column(name = "pressao_arterial")
    private String pressaoArterial;

    @Column(name = "frequencia_cardiaca")
    private Integer frequenciaCardiaca;

    @Column(name = "circunferencia_abdominal")
    private Double circunferenciaAbdominal;

    @Lob
    @Column(name = "observacoes")
    private String observacoes;

    @PrePersist
    public void prePersist() {
        if (this.dataAvaliacao == null) {
            this.dataAvaliacao = LocalDate.now();
        }
        calcularImc();
    }

    @PreUpdate
    public void preUpdate() {
        calcularImc();
    }

    private void calcularImc() {
        if (this.altura != null && this.altura > 0 && this.peso != null) {
            this.imc = this.peso / (this.altura * this.altura);
        }
    }
}