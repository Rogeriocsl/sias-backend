package br.com.sias.api.dto;

public record EnderecoResponse(
        Long id,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep,
        String complemento
) {}