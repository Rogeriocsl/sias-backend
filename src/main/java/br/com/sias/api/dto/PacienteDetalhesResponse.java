package br.com.sias.api.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PacienteDetalhesResponse {

    private DadosCadastrais dados;
    private List<PresencaItem> historicoPresenca;
    private List<AvaliacaoFisicaResponse> evolucoes;

    @Data
    @Builder
    public static class DadosCadastrais {
        private String nome;
        private String cpf;
        private String telefone;
        private String sexo;
        private String ubsf;
        private String turma;
        private String dataNascimento;
        private List<String> condicoesSaude;
    }

    @Data
    @Builder
    public static class PresencaItem {
        private String data;
        private boolean presente;
        private String status;
    }
}