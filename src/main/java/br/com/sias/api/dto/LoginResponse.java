package br.com.sias.api.dto;

import br.com.sias.api.model.enums.Perfil;

public class LoginResponse {
    private String token;
    private String login;
    private Perfil perfil;

    public LoginResponse(String token, String login, Perfil perfil) {
        this.token = token;
        this.login = login;
        this.perfil = perfil;
    }

    public String getToken() { return token; }
    public String getLogin() { return login; }
    public Perfil getPerfil() { return perfil; }
}