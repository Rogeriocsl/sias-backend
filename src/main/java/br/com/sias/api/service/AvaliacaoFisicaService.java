package br.com.sias.api.service;

import br.com.sias.api.dto.AvaliacaoFisicaRequest;
import br.com.sias.api.dto.AvaliacaoFisicaResponse;
import br.com.sias.api.model.AvaliacaoFisica;
import br.com.sias.api.model.Paciente;
import br.com.sias.api.repository.AvaliacaoFisicaRepository;
import br.com.sias.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoFisicaService {
    @Autowired
    private AvaliacaoFisicaRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public AvaliacaoFisicaResponse criar(AvaliacaoFisicaRequest dto) {
        AvaliacaoFisica avaliacao = converterParaEntity(dto);
        AvaliacaoFisica salvo = repository.save(avaliacao);
        return converterParaResponse(salvo);
    }

    public AvaliacaoFisicaResponse atualizar(Long id, AvaliacaoFisicaRequest dto) {
        AvaliacaoFisica existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        existente.setPaciente(paciente);
        existente.setPeso(dto.getPeso());
        existente.setAltura(dto.getAltura());
        existente.setPressaoArterial(dto.getPressaoArterial());
        existente.setFrequenciaCardiaca(dto.getFrequenciaCardiaca());
        existente.setCircunferenciaAbdominal(dto.getCircunferenciaAbdominal());
        existente.setObservacoes(dto.getObservacoes());

        AvaliacaoFisica atualizado = repository.save(existente);

        return converterParaResponse(atualizado);
    }

    public List<AvaliacaoFisica> listar() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Avaliação não encontrada");
        }
        repository.deleteById(id);
    }

    public List<AvaliacaoFisicaResponse> buscarPorPaciente(Long pacienteId) {
        return repository.findByPacienteIdOrderByDataAvaliacaoDesc(pacienteId).stream().map(this::converterParaResponse).toList();
    }

    private AvaliacaoFisica converterParaEntity(AvaliacaoFisicaRequest dto) {
        AvaliacaoFisica avaliacao = new AvaliacaoFisica();
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        avaliacao.setPaciente(paciente);
        avaliacao.setPeso(dto.getPeso());
        avaliacao.setAltura(dto.getAltura());
        avaliacao.setPressaoArterial(dto.getPressaoArterial());
        avaliacao.setFrequenciaCardiaca(dto.getFrequenciaCardiaca());
        avaliacao.setCircunferenciaAbdominal(dto.getCircunferenciaAbdominal());
        avaliacao.setObservacoes(dto.getObservacoes());

        return avaliacao;
    }

    private AvaliacaoFisicaResponse converterParaResponse(AvaliacaoFisica avaliacao) {
        AvaliacaoFisicaResponse resp = new AvaliacaoFisicaResponse();

        resp.setId(avaliacao.getId());
        resp.setPacienteId(avaliacao.getPaciente().getId());
        resp.setDataAvaliacao(avaliacao.getDataAvaliacao());
        resp.setPeso(avaliacao.getPeso());
        resp.setAltura(avaliacao.getAltura());
        resp.setImc(avaliacao.getImc());
        resp.setPressaoArterial(avaliacao.getPressaoArterial());
        resp.setFrequenciaCardiaca(avaliacao.getFrequenciaCardiaca());
        resp.setCircunferenciaAbdominal(avaliacao.getCircunferenciaAbdominal());
        resp.setObservacoes(avaliacao.getObservacoes());

        return resp;
    }
}