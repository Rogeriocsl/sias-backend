package br.com.sias.api.dto;


import br.com.sias.api.model.Presenca;
import br.com.sias.api.model.enums.StatusPresenca;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter

public class PresencaResponse {

    private Long id;
    private Long pacienteId;
    private LocalDate dataPresenca;
    private StatusPresenca status;
    private String observacao;

}