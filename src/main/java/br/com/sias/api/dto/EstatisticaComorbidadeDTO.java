package br.com.sias.api.dto;
import br.com.sias.api.model.enums.DoencaCronica;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstatisticaComorbidadeDTO {
    private DoencaCronica comorbidade;
    private Long quantidade;
}