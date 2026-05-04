package br.com.sias.api.dto;

import br.com.sias.api.model.enums.StatusPresenca;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class PresencaRequest{
        private Long pacienteId;
        private LocalDate dataPresenca;
        private StatusPresenca status; // O tipo aqui é o Enum!
        private String observacao;
}