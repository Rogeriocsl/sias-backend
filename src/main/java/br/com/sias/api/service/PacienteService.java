package br.com.sias.api.service;

import br.com.sias.api.dto.*;
import br.com.sias.api.exception.NotFoundException;
import br.com.sias.api.model.*;
import br.com.sias.api.model.enums.EncaminhamentoMotivo;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import br.com.sias.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    @Autowired
    private UnidadeBasicaSaudeRepository unidadeRepository;

    @Autowired
    private EncaminhamentoRepository encaminhamentosRepository;

    @Autowired
    private AvaliacaoFisicaRepository avaliacoesRepository;

    @Autowired
    private TurmasRepository turmasRepository;

    @Autowired
    private PresencaRepository presencaRepository;

    public PacienteResponse BuscarComHistorico(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não Encontrado"));

        PacienteResponse pacienteResponse = converterParaResponse(paciente);

        pacienteResponse.setEncaminhamentos(
                encaminhamentosRepository.findByPacienteId(id).stream()
                        .map(this::converterEncaminhamentoParaResponse)
                        .toList()
        );

        pacienteResponse.setAvaliacoes(
                avaliacoesRepository.findByPacienteId(id).stream()
                        .map(this::converterAvaliacaoParaResponse)
                        .toList()
        );

        return pacienteResponse;
    }

    public PacienteDetalhesResponse buscarDetalhes(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        PacienteDetalhesResponse.DadosCadastrais dados =
                PacienteDetalhesResponse.DadosCadastrais.builder()
                        .nome(paciente.getNome())
                        .cpf(paciente.getCpf())
                        .telefone(paciente.getTelefone())
                        .sexo(paciente.getGenero() != null ? paciente.getGenero().name() : null)
                        .ubsf(paciente.getUnidadeOrigem() != null
                                ? paciente.getUnidadeOrigem().getNomeUnidade() : null)
                        .turma(paciente.getTurmas() != null
                                ? paciente.getTurmas().getNome() : null)
                        .dataNascimento(paciente.getDataNascimento() != null
                                ? paciente.getDataNascimento().toString() : null)
                        .condicoesSaude(paciente.getCondicoesSaude().stream()
                                .map(Enum::name)
                                .toList())
                        .build();

        List<PacienteDetalhesResponse.PresencaItem> historico =
                presencaRepository.findByPacienteId(id).stream()
                        .map(p -> PacienteDetalhesResponse.PresencaItem.builder()
                                .data(p.getDataPresenca().toString())
                                .presente(p.getStatus().name().equals("PRESENTE"))
                                .status(p.getStatus().name())
                                .build())
                        .toList();

        List<AvaliacaoFisicaResponse> evolucoes =
                avaliacoesRepository.findByPacienteId(id).stream()
                        .map(this::converterAvaliacaoParaResponse)
                        .toList();

        return PacienteDetalhesResponse.builder()
                .dados(dados)
                .historicoPresenca(historico)
                .evolucoes(evolucoes)
                .build();
    }

    public PacienteResponse criar(PacienteRequest dto) {
        Paciente paciente = converterParaEntity(dto);
        Paciente salvo = repository.save(paciente);
        return converterParaResponse(salvo);
    }

    public PacienteResponse atualizar(Long id, PacienteRequest dto) {
        Paciente existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        if (dto.getUnidadeId() == null) {
            throw new IllegalArgumentException("A Unidade Básica de Saúde (UBS) é obrigatória.");
        }

        UnidadeBasicaSaude ubs = unidadeRepository.findById(dto.getUnidadeId())
                .orElseThrow(() -> new NotFoundException("Unidade Básica de Saúde não encontrada"));

        existente.setNome(dto.getNome());
        existente.setCpf(dto.getCpf());
        existente.setDataNascimento(dto.getDataNascimento());
        existente.setTelefone(dto.getTelefone());
        existente.setGenero(dto.getGenero());
        existente.setTipoSanguineo(dto.getTipoSanguineo());
        existente.setCondicoesSaude(dto.getCondicoesSaude());
        existente.setUnidadeOrigem(ubs);

        if (dto.getTurmaId() != null) {
            Turmas turma = turmasRepository.findById(dto.getTurmaId()).orElse(null);
            existente.setTurmas(turma);
        } else {
            existente.setTurmas(null);
        }

        Paciente atualizado = repository.save(existente);
        return converterParaResponse(atualizado);
    }

    public List<PacienteResponse> listar() {
        return repository.findAll().stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Paciente não encontrado");
        }
        repository.deleteById(id);
    }

    public PacienteResponse buscarPorId(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));
        return converterParaResponse(paciente);
    }

    public List<PacienteResponse> listarPacientesDaTurma(Long turmaId) {
        return repository.findByTurmasId(turmaId).stream()
                .map(this::converterParaResponse)
                .toList();
    }

    private Paciente converterParaEntity(PacienteRequest dto) {
        Paciente paciente = new Paciente();

        if (dto.getUnidadeId() == null) {
            throw new IllegalArgumentException("A Unidade Básica de Saúde (UBS) é obrigatória.");
        }

        UnidadeBasicaSaude ubs = unidadeRepository.findById(dto.getUnidadeId())
                .orElseThrow(() -> new NotFoundException("Unidade Básica de Saúde não encontrada"));

        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setTelefone(dto.getTelefone());
        paciente.setGenero(dto.getGenero());
        paciente.setTipoSanguineo(dto.getTipoSanguineo());
        paciente.setCondicoesSaude(dto.getCondicoesSaude());
        paciente.setUnidadeOrigem(ubs);

        if (dto.getTurmaId() != null) {
            Turmas turma = turmasRepository.findById(dto.getTurmaId()).orElse(null);
            paciente.setTurmas(turma);
        }

        Encaminhamento encaminhamento = new Encaminhamento();
        encaminhamento.setPaciente(paciente);
        encaminhamento.setMotivo(EncaminhamentoMotivo.OUTRO);
        encaminhamento.setStatus(EncaminhamentoStatus.PENDENTE);
        encaminhamento.setDataEncaminhamento(LocalDate.now());
        encaminhamento.setObservacoes("");

        List<Encaminhamento> listaEncaminhamentos = new ArrayList<>();
        listaEncaminhamentos.add(encaminhamento);
        paciente.setEncaminhamentos(listaEncaminhamentos);

        return paciente;
    }

    private PacienteResponse converterParaResponse(Paciente paciente) {
        PacienteResponse resp = new PacienteResponse();

        resp.setId(paciente.getId());
        resp.setNome(paciente.getNome());
        resp.setCpf(paciente.getCpf());
        resp.setDataNascimento(paciente.getDataNascimento());
        resp.setTelefone(paciente.getTelefone());
        resp.setGenero(paciente.getGenero());
        resp.setTipoSanguineo(paciente.getTipoSanguineo());
        resp.setUnidadeId(paciente.getUnidadeOrigem().getId());
        resp.setTurmaId(paciente.getTurmas() != null ? paciente.getTurmas().getId() : null);
        resp.setCondicoesSaude(
                paciente.getCondicoesSaude().stream()
                        .map(Enum::name)
                        .toList()
        );

        return resp;
    }

    private EncaminhamentoResponse converterEncaminhamentoParaResponse(Encaminhamento e) {
        EncaminhamentoResponse r = new EncaminhamentoResponse();
        r.setId(e.getId());
        r.setPacienteId(e.getPaciente().getId());
        r.setMotivo(e.getMotivo().name());
        r.setStatus(e.getStatus().name());
        r.setDataEncaminhamento(e.getDataEncaminhamento());
        r.setObservacoes(e.getObservacoes());
        return r;
    }

    private AvaliacaoFisicaResponse converterAvaliacaoParaResponse(AvaliacaoFisica a) {
        AvaliacaoFisicaResponse r = new AvaliacaoFisicaResponse();
        r.setId(a.getId());
        r.setPacienteId(a.getPaciente().getId());
        r.setDataAvaliacao(a.getDataAvaliacao());
        r.setPeso(a.getPeso());
        r.setAltura(a.getAltura());
        r.setImc(a.getImc());
        r.setPressaoArterial(a.getPressaoArterial());
        r.setFrequenciaCardiaca(a.getFrequenciaCardiaca());
        r.setCircunferenciaAbdominal(a.getCircunferenciaAbdominal());
        r.setObservacoes(a.getObservacoes());
        return r;
    }
}