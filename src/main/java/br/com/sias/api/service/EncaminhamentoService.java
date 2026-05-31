package br.com.sias.api.service;

import br.com.sias.api.dto.EncaminhamentoRequest;
import br.com.sias.api.dto.EncaminhamentoResponse;
import br.com.sias.api.model.Encaminhamento;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.model.Turmas;
import br.com.sias.api.model.enums.EncaminhamentoMotivo;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import br.com.sias.api.repository.EncaminhamentoRepository;
import br.com.sias.api.repository.PacienteRepository;
import br.com.sias.api.repository.TurmasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EncaminhamentoService {

    @Autowired
    private EncaminhamentoRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TurmasRepository turmasRepository;

    // ── Converter ─────────────────────────────────────────────────────────
    private EncaminhamentoResponse converterParaResponse(Encaminhamento e) {
        EncaminhamentoResponse r = new EncaminhamentoResponse();

        r.setId(e.getId());
        r.setMotivo(e.getMotivo().name());
        r.setStatus(e.getStatus().name());          // enum → String para o response
        r.setDataEncaminhamento(e.getDataEncaminhamento());
        r.setObservacoes(e.getObservacoes());

        if (e.getPaciente() != null) {
            r.setPacienteId(e.getPaciente().getId());
            r.setPacienteNome(e.getPaciente().getNome());
            r.setPacienteCpf(e.getPaciente().getCpf());
        }

        if (e.getTurma() != null) {
            r.setTurmaId(e.getTurma().getId());
            r.setTurmaNome(e.getTurma().getNome());
        }

        return r;
    }

    private Encaminhamento converterParaEntity(EncaminhamentoRequest dto) {
        Encaminhamento enc = new Encaminhamento();

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        enc.setPaciente(paciente);
        enc.setMotivo(EncaminhamentoMotivo.valueOf(dto.getMotivo()));
        enc.setDataEncaminhamento(dto.getDataEncaminhamento());
        enc.setStatus(EncaminhamentoStatus.PENDENTE);   // novo encaminhamento começa PENDENTE
        enc.setObservacoes(dto.getObservacoes());

        if (dto.getTurmaId() != null) {
            Turmas turma = turmasRepository.findById(dto.getTurmaId())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            enc.setTurma(turma);
        }

        return enc;
    }

    // ── CRUD ──────────────────────────────────────────────────────────────

    public EncaminhamentoResponse criar(EncaminhamentoRequest dto) {
        return converterParaResponse(repository.save(converterParaEntity(dto)));
    }

    public List<EncaminhamentoResponse> listar() {
        return repository.findAllComRelacoes()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public EncaminhamentoResponse atualizar(Long id, EncaminhamentoRequest dto) {
        Encaminhamento enc = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encaminhamento não encontrado"));

        // String → enum com valueOf (lança IllegalArgumentException se inválido)
        if (dto.getStatus() != null) {
            enc.setStatus(EncaminhamentoStatus.valueOf(dto.getStatus()));
        }

        enc.setObservacoes(dto.getObservacoes());

        if (dto.getTurmaId() != null) {
            Turmas turma = turmasRepository.findById(dto.getTurmaId())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            enc.setTurma(turma);

            // Sincroniza a turma no paciente também
            Paciente paciente = enc.getPaciente();
            paciente.setTurmas(turma);
            pacienteRepository.save(paciente);
        } else {
            enc.setTurma(null);
        }

        return converterParaResponse(repository.save(enc));
    }

    public List<EncaminhamentoResponse> buscarEncaminhamento(Long pacienteId) {
        return repository.findByPacienteIdOrderByDataEncaminhamentoDesc(pacienteId)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public List<EncaminhamentoResponse> listarEncaminhamentos(EncaminhamentoStatus status) {
        return repository.findByStatus(status)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Encaminhamento não encontrado");
        }
        repository.deleteById(id);
    }
}