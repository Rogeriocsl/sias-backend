package br.com.sias.api.dto;

import br.com.sias.api.model.enums.Perfil;

public class LoginResponse {
    private String token;
    private String login;
    private Perfil perfil;
    private String nome;

    public LoginResponse(String token, String login, Perfil perfil, String nome) {
        this.token = token;
        this.login = login;
        this.perfil = perfil;
        this.nome = nome;
    }

    public String getToken() { return token; }
    public String getNome() { return nome; }
    public String getLogin() { return login; }
    public Perfil getPerfil() { return perfil; }
}