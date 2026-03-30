package br.com.sias.api.model;

import br.com.sias.api.model.enums.Perfis;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank(message = "O login é obrigatório")
    @Size(min = 11, max = 40)
    @Column(nullable = false, unique = true, length = 40)
    private String login;

    @NotBlank(message = "A senha é obrigatório")
    @Column(nullable = false, length = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @NotBlank(message = "O email é obrigatório")
    @Size(min = 11, max = 40)
    @Column(nullable = false, unique = true, length = 40)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", length = 10)
    private Perfis perfis;


}
