package br.com.sias.api.model;

import br.com.sias.api.model.enums.StatusPresenca;
import jakarta.persistence.*;
import lombok.*; // Importa todas as anotações do Lombok
import java.time.LocalDate;

@Entity
@Table(name = "presencas") // Mudado de encaminhamentos para presencas
@Getter
@Setter
@NoArgsConstructor // Construtor sem argumentos
@AllArgsConstructor // Construtor com todos os argumentos
@Builder // Permite usar o padrão Builder (ex: Presenca.builder()...)
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

}