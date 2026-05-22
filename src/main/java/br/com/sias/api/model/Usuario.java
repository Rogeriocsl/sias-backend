package br.com.sias.api.model;

import br.com.sias.api.model.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 150, message = "Nome deve ter no minimo 3 caracteres" )
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank(message = "O login é obrigatório")
    @Size(min = 11, max = 40, message = "Login deve ter no minimo 11 caracteres e no maximo 40")
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
    @NotNull(message = "Perfil deve ser selecionado")
    @Column(name = "perfil", length = 10)
    private Perfil perfil;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(perfil.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
