package br.com.sias.api.dto;

import br.com.sias.api.model.enums.Perfil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
    private String nome;
    private String login;
    private String senha;
    private String email;
    private Perfil perfil;

}
