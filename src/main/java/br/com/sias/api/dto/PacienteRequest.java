package br.com.sias.api.dto;

import br.com.sias.api.model.enums.DoencaCronica;
import br.com.sias.api.model.enums.Genero;
import br.com.sias.api.model.enums.TipoSanguineo;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PacienteRequest {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private Genero genero;
    private TipoSanguineo tipoSanguineo;;
    private Long unidadeId;
    private List<DoencaCronica> condicoesSaude;
    private Long turmaId;
}