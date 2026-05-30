package br.com.sias.api.service;

import br.com.sias.api.dto.AvaliacaoFisicaResponse;
import br.com.sias.api.dto.EncaminhamentoResponse;
import br.com.sias.api.dto.PacienteRequest;
import br.com.sias.api.dto.PacienteResponse;
import br.com.sias.api.exception.NotFoundException;
import br.com.sias.api.model.*;
import br.com.sias.api.model.enums.EncaminhamentoMotivo;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import br.com.sias.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PacienteResponse BuscarComHistorico(Long id) {
        Paciente paciente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente não Encontrado"));

        PacienteResponse pacienteResponse = new PacienteResponse();

        pacienteResponse.setId(paciente.getId());
        pacienteResponse.setNome(paciente.getNome());
        pacienteResponse.setCpf(paciente.getCpf());
        pacienteResponse.setDataNascimento(paciente.getDataNascimento());
        pacienteResponse.setTelefone(paciente.getTelefone());
        pacienteResponse.setGenero(paciente.getGenero());
        pacienteResponse.setTipoSanguineo(paciente.getTipoSanguineo());
        pacienteResponse.setUnidadeId(paciente.getUnidadeOrigem().getId());
        pacienteResponse.setTurmaId(paciente.getTurmas().getId());
        pacienteResponse.setCondicoesSaude(
                paciente.getCondicoesSaude().stream()
                        .map(Enum::name)
                        .toList()
        );

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


    public PacienteResponse criar(PacienteRequest dto) {
        Paciente paciente = converterParaEntity(dto);
        Paciente salvo = repository.save(paciente);
        return converterParaResponse(salvo);
    }

    public PacienteResponse atualizar(Long id, PacienteRequest dto) {
        Paciente existente = repository.findById(id).orElseThrow(() -> new NotFoundException("Paciente não encontrado"));
        UnidadeBasicaSaude ubs = unidadeRepository.findById(dto.getUnidadeId()).orElseThrow(() -> new NotFoundException("Unidade Básica de Saúde não encontrada"));
        Turmas turmas = turmasRepository.findById(dto.getTurmaId()).orElseThrow(() -> new NotFoundException("Turma não encontrada"));

        existente.setNome(dto.getNome());
        existente.setCpf(dto.getCpf());
        existente.setDataNascimento(dto.getDataNascimento());
        existente.setTelefone(dto.getTelefone());
        existente.setGenero(dto.getGenero());
        existente.setTipoSanguineo(dto.getTipoSanguineo());
        existente.setCondicoesSaude(dto.getCondicoesSaude());
        existente.setUnidadeOrigem(ubs);
        existente.setTurmas(turmas);

        if (dto.getEncaminhamentos() != null && !dto.getEncaminhamentos().isEmpty()) {
            var eDto = dto.getEncaminhamentos().get(0);
            Encaminhamento enc;
            if (existente.getEncaminhamentos() != null && !existente.getEncaminhamentos().isEmpty()) {
                enc = existente.getEncaminhamentos().get(existente.getEncaminhamentos().size() - 1);
            } else {
                enc = new Encaminhamento();
                enc.setPaciente(existente);
                existente.getEncaminhamentos().add(enc);
            }

            enc.setDataEncaminhamento(eDto.getDataEncaminhamento());
            enc.setMotivo(br.com.sias.api.model.enums.EncaminhamentoMotivo.valueOf(eDto.getMotivo()));
            enc.setStatus(eDto.getStatus());
            enc.setObservacoes(eDto.getObservacoes());
        }

        Paciente atualizado = repository.save(existente);

        return converterParaResponse(atualizado);
    }

    public List<PacienteResponse> listar() {
        List<Paciente> pacientes = repository.findAll();

        return pacientes.stream()
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
        Paciente paciente = repository.findById(id).orElseThrow(() -> new NotFoundException("Paciente não encontrado"));
        return converterParaResponse(paciente);
    }

    private Paciente converterParaEntity(PacienteRequest dto) {
        Paciente paciente = new Paciente();
        UnidadeBasicaSaude ubs = unidadeRepository.findById(dto.getUnidadeId()).orElseThrow(() -> new NotFoundException("Unidade Básica de Saúde não encontrada"));
        Turmas turmas = turmasRepository.findById(dto.getTurmaId()).orElseThrow(() -> new NotFoundException("Turma não encontrada"));

        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setTelefone(dto.getTelefone());
        paciente.setGenero(dto.getGenero());
        paciente.setTipoSanguineo(dto.getTipoSanguineo());
        paciente.setCondicoesSaude(dto.getCondicoesSaude());
        paciente.setUnidadeOrigem(ubs);
        paciente.setTurmas(turmas);

        if (dto.getEncaminhamentos() != null && !dto.getEncaminhamentos().isEmpty()) {
            List<Encaminhamento> listaEncaminhamentos = dto.getEncaminhamentos().stream().map(eDto -> {
                Encaminhamento enc = new Encaminhamento();
                enc.setDataEncaminhamento(eDto.getDataEncaminhamento());
                enc.setMotivo(br.com.sias.api.model.enums.EncaminhamentoMotivo.valueOf(eDto.getMotivo()));
                enc.setStatus(eDto.getStatus());
                enc.setObservacoes(eDto.getObservacoes());
                enc.setPaciente(paciente);

                return enc;
            }).toList();

            paciente.setEncaminhamentos(listaEncaminhamentos);
        }

        return paciente; // 👈 O return final fica AQUI, no fechamento do método!
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
        resp.setTurmaId(paciente.getTurmas().getId());
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