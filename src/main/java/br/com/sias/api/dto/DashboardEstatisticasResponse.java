package br.com.sias.api.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DashboardEstatisticasResponse {

    // ── Campos existentes (não alterar) ──────────────────────────────────────
    private long totalPacientes;
    private long encaminhamentosPendentes;
    private List<EstatisticaUbsDTO>          distribuicaoPorUbs;
    private List<EstatisticaComorbidadeDTO>  distribuicaoPorComorbidade;
    private EstatisticaEvolucaoDTO           metricasDeEvolucao;

    // ── Campos novos ─────────────────────────────────────────────────────────
    private long totalPacientesAtivos;           // com encaminhamento EM_ACOMPANHAMENTO
    private long pendentesHaMaisDe15Dias;        // alerta: sem turma por > 15 dias
    private long pacientesComFaltasConsecutivas; // alerta: 3+ faltas nos últimos 30 dias

    private List<EstatisticaOcupacaoTurmaDTO>    ocupacaoTurmas;
    private List<EstatisticaPresencaSemanaDTO>  presencaSemanal;
}