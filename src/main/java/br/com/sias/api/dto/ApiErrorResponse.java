package br.com.sias.api.dto;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    private int status;
    private String erro;
    private LocalDateTime data;

    public ApiErrorResponse(int status, String erro) {
        this.status = status;
        this.erro = erro;
        this.data = LocalDateTime.now();
    }

    public int getStatus() { return status; }
    public String getErro() { return erro; }
    public LocalDateTime getData() { return data; }
}