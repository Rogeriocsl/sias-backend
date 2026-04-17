package br.com.sias.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoRequest(

        Long id,

        @NotBlank(message = "Rua obrigatória")
        String rua,

        @NotBlank(message = "Número obrigatório")
        String numero,

        @NotBlank(message = "Bairro obrigatório")
        String bairro,

        @NotBlank(message = "Cidade obrigatório")
        String cidade,

        @NotBlank(message = "Estado obrigatório")
        String estado,

        @Pattern(regexp = "\\d{8}", message = "CEP deve ter 8 dígitos")
        String cep,

        String complemento
) {}
