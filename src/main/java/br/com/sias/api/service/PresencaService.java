package br.com.sias.api.service;

import br.com.sias.api.dto.PresencaRequest;
import br.com.sias.api.dto.PresencaResponse;
import br.com.sias.api.exception.NotFoundException;
import br.com.sias.api.model.Presenca;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.repository.PresencaRepository;
import br.com.sias.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class PresencaService {
    @Autowired
    private PresencaRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public PresencaResponse criar(PresencaRequest dto) {
        if (dto.getAtividade() == null) {
            throw new NotFoundException("A atividade física deve ser vinculada");
        }

        if (repository.existsByPacienteIdAndDataPresenca(dto.getPacienteId(),dto.getDataPresenca())){
            throw new RuntimeException("O paciente já tem uma presença registrada nesta data");
        }
        Presenca presenca = converterParaEntity(dto);
        Presenca salvo = repository.save(presenca);
        return converterParaResponse(salvo);
    }

    public PresencaResponse atualizar(Long id, PresencaRequest dto) {
        Presenca existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Presença não registrada"));
        Paciente pacienteId = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        existente.setPaciente(pacienteId);
        existente.setDataPresenca(dto.getDataPresenca());
        existente.setStatus(dto.getStatus());
        existente.setObservacao(dto.getObservacao());
        existente.setAtividade(dto.getAtividade());

        Presenca presenca = repository.save(existente);

        return converterParaResponse(presenca);
    }

    public List<PresencaResponse> listar() {
        List<Presenca> presencas = repository.findAll();

        return presencas.stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public Optional<PresencaResponse> buscarPorPacienteEData(Long pacienteId, LocalDate data) {
        return repository.findByPacienteIdAndDataPresenca(pacienteId, data)
                .map(this::converterParaResponse);
    }
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Presença não encontrada");
        }
        repository.deleteById(id);
    }

    public PresencaResponse buscarPorId(Long id) {
        Presenca presenca = repository.findById(id).orElseThrow(() -> new NotFoundException("Presença não encontrado"));
        return converterParaResponse(presenca);
    }

    public List<Presenca> PresencaDoDiaAtual(){
        LocalDate hoje = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        return repository.findByDataPresenca(hoje);
    }

    public List<Presenca> historicoPresencaParciente(Long parcienteId){
        return repository.findByPacienteIdOrderByDataPresencaDesc(parcienteId);
    }

    private Presenca converterParaEntity(PresencaRequest dto) {
        Presenca presenca = new Presenca();
        Paciente pacienteId = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        presenca.setPaciente(pacienteId);
        presenca.setDataPresenca(dto.getDataPresenca());
        presenca.setStatus(dto.getStatus());
        presenca.setObservacao(dto.getObservacao());
        presenca.setAtividade(dto.getAtividade());

        return presenca;
    }

    private PresencaResponse converterParaResponse(Presenca presenca) {
        PresencaResponse response = new PresencaResponse();

        response.setPacienteId(presenca.getPaciente().getId());
        response.setId(presenca.getId());
        response.setDataPresenca(presenca.getDataPresenca());
        response.setStatus(presenca.getStatus());
        response.setObservacao(presenca.getObservacao());
        response.setAtividade(presenca.getAtividade());

        return response;
    }
}