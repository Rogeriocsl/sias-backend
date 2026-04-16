package br.com.sias.api.model.enums;

public enum EncaminhamentoStatus {
    PENDENTE("Aguardando início"),
    EM_ACOMPANHAMENTO("Em acompanhamento na academia"),
    FINALIZADO("Ciclo concluído"),
    CANCELADO("Encaminhamento cancelado");

    private final String descricao;

    EncaminhamentoStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
