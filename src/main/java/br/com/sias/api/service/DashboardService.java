package br.com.sias.api.service;

import br.com.sias.api.dto.*;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import br.com.sias.api.repository.*;
import br.com.sias.api.repository.projection.EvolucaoAvaliacaoProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    @Autowired private PacienteRepository       pacienteRepository;
    @Autowired private EncaminhamentoRepository  encaminhamentoRepository;
    @Autowired private AvaliacaoFisicaRepository avaliacaoRepository;
    @Autowired private PresencaRepository        presencaRepository;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM");


    public DashboardEstatisticasResponse obterEstatisticasAvancadas() {
        LocalDate hoje  = LocalDate.now();

        return DashboardEstatisticasResponse.builder()
                .totalPacientes(pacienteRepository.count())
                .totalPacientesAtivos(pacienteRepository.countPacientesAtivos())
                .encaminhamentosPendentes(
                        encaminhamentoRepository.countByStatus(EncaminhamentoStatus.PENDENTE))
                .pendentesHaMaisDe15Dias(
                        encaminhamentoRepository.countPendentesAtrasados(hoje.minusDays(15)))
                .pacientesComFaltasConsecutivas(
                        presencaRepository.findPacientesComFaltasConsecutivas(hoje.minusDays(30)).size())
                .distribuicaoPorUbs(pacienteRepository.contarPacientesPorUbs())
                .distribuicaoPorComorbidade(pacienteRepository.contarPacientesPorComorbidade())
                .ocupacaoTurmas(encaminhamentoRepository.findOcupacaoPorTurma())
                .metricasDeEvolucao(calcularEvolucao())
                .presencaSemanal(calcularPresencaSemanal(hoje))
                .build();
    }


    private EstatisticaEvolucaoDTO calcularEvolucao() {
        List<EvolucaoAvaliacaoProjection> dados = avaliacaoRepository.findEvolucaoPorPaciente();

        if (dados.isEmpty()) {
            return EstatisticaEvolucaoDTO.builder()
                    .totalPacientesAvaliados(0).build();
        }

        int reduziuPeso = 0, piorou = 0, estabilizou = 0;
        int reduziuImc  = 0, reduziuCirc = 0;
        double somaPerdaPeso = 0, somaImc = 0, somaCirc = 0;
        int comMelhoraPeso = 0;

        for (EvolucaoAvaliacaoProjection p : dados) {
            double deltaPeso = nvl(p.getPesoFinal())           - nvl(p.getPesoInicial());
            double deltaImc  = nvl(p.getImcInicial())          - nvl(p.getImcFinal());
            double deltaCirc = nvl(p.getCircAbdominalInicial()) - nvl(p.getCircAbdominalFinal());

            if      (deltaPeso < -0.5) { reduziuPeso++; somaPerdaPeso += Math.abs(deltaPeso); comMelhoraPeso++; }
            else if (deltaPeso >  0.5) { piorou++; }
            else                       { estabilizou++; }

            if (deltaImc  > 0) reduziuImc++;
            if (deltaCirc > 0) reduziuCirc++;

            somaImc  += deltaImc;
            somaCirc += deltaCirc;
        }

        int total = dados.size();

        return EstatisticaEvolucaoDTO.builder()
                .totalPacientesAvaliados(total)
                .pacientesComReducaoPeso(reduziuPeso)
                .pacientesComReducaoIMC(reduziuImc)
                .percentualSucessoPeso(pct(reduziuPeso, total))
                .pacientesComReducaoCircAbdominal(reduziuCirc)
                .pacientesComPioraDepeso(piorou)
                .pacientesEstabilizados(estabilizou)
                .mediaPerdaPesoKg(comMelhoraPeso > 0 ? arred(somaPerdaPeso / comMelhoraPeso) : 0.0)
                .mediaReducaoImc(arred(somaImc   / total))
                .mediaReducaoCircAbdominal(arred(somaCirc / total))
                .percentualSucessoImc(pct(reduziuImc,  total))
                .percentualSucessoCircAbdominal(pct(reduziuCirc, total))
                .build();
    }


    private List<EstatisticaPresencaSemanaDTO> calcularPresencaSemanal(LocalDate hoje) {
        LocalDate dataInicio = hoje.minusDays(28);
        List<Object[]> rows  = presencaRepository.findPresencaAgrupadaPorSemana(dataInicio);

        Map<String, Map<String, Long>> porSemana = new TreeMap<>();
        for (Object[] row : rows) {
            String semana = row[0].toString();
            String status = row[1].toString();
            long   qtd    = ((Number) row[2]).longValue();
            porSemana.computeIfAbsent(semana, k -> new HashMap<>()).put(status, qtd);
        }

        return porSemana.entrySet().stream()
                .map(e -> {
                    LocalDate inicio = LocalDate.parse(e.getKey());
                    String label     = inicio.format(FMT) + " – " + inicio.plusDays(6).format(FMT);
                    Map<String, Long> m = e.getValue();
                    return new EstatisticaPresencaSemanaDTO(
                            label,
                            m.getOrDefault("PRESENTE",   0L),
                            m.getOrDefault("FALTA",       0L),
                            m.getOrDefault("JUSTIFICADA", 0L)
                    );
                })
                .toList();
    }


    private static double nvl(Double v)           { return v != null ? v : 0.0; }
    private static double arred(double v)         { return Math.round(v * 10.0) / 10.0; }
    private static double pct(int parte, int tot) {
        return tot > 0 ? arred((double) parte / tot * 100) : 0.0;
    }
}